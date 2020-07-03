package com.shareeverything.config;

import com.shareeverything.Util.ShareEverythingUtils;
import com.shareeverything.dto.UserDto;
import com.shareeverything.dto.request.PostRequestDto;
import com.shareeverything.entity.Post;
import com.shareeverything.entity.User;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.UUID;

@Configuration
public class OrikaMapperConfig {

    @Bean
    public MapperFactory mapperFactory() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.getConverterFactory().registerConverter("uuid-string", new StringUUID());
        mapperFactory.getConverterFactory().registerConverter("date-string",new DateString());



        mapperFactory.classMap(PostRequestDto.class, Post.class)
                .fieldMap("startDate","startDate").converter("date-string").add()
                .fieldMap("expireDate","expireDate").converter("date-string").add()
                .byDefault().register();


        return mapperFactory;
    }

    private class StringUUID extends BidirectionalConverter<String, UUID> {
        @Override
        public UUID convertTo(String s, Type<UUID> type, MappingContext mappingContext) {
            return UUID.fromString(s);
        }

        @Override
        public String convertFrom(UUID uuid, Type<String> type, MappingContext mappingContext) {
            return uuid.toString();
        }
    }

    private class DateString extends BidirectionalConverter<Date, String> {

        @Override
        public String convertTo(Date date, Type<String> type, MappingContext mappingContext) {
            return ShareEverythingUtils.getDateAsString(date);
        }

        @Override
        public Date convertFrom(String s, Type<Date> type, MappingContext mappingContext) {
            return ShareEverythingUtils.getStringAsDate(s);
        }

    }

}
