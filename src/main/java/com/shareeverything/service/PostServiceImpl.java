package com.shareeverything.service;

import com.shareeverything.constant.ProductStatus;
import com.shareeverything.constant.ResponseStatus;
import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.request.PostRequestDto;
import com.shareeverything.dto.response.PostResponseDto;
import com.shareeverything.entity.*;
import com.shareeverything.exception.ShareEverythingException;
import com.shareeverything.repository.*;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    MapperFactory mapperFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ZoneRepository zoneRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    ChargingScheduleRepository chargingScheduleRepository;

    @Override
    public BaseResponseDto addPost(PostRequestDto postRequestDto,String userId) {

        User user = userRepository.getOne(UUID.fromString(userId));

        if(user == null){
            throw new ShareEverythingException("Post added failed. User not found.");
        }

        Post post = mapperFactory.getMapperFacade().map(postRequestDto,Post.class);

        post.setUser(user);

        Zone zone = zoneRepository.getOne(UUID.fromString(postRequestDto.getZoneId()));
        post.setZone(zone);

        City city = cityRepository.getOne(UUID.fromString(postRequestDto.getCityId()));
        post.setCity(city);

        Category category = categoryRepository.getOne(UUID.fromString(postRequestDto.getCategoryId()));
        post.setCategory(category);

        if(postRequestDto.getSubCategoryId() != null && !StringUtils.isEmpty(postRequestDto.getSubCategoryId())){
            SubCategory subCategory = subCategoryRepository.getOne(UUID.fromString(postRequestDto.getSubCategoryId()));
            post.setSubCategory(subCategory);
            log.info("Sub-category added. Sub-category:{}",post.getSubCategory().getName());
        }

        ChargingSchedule chargingSchedule = chargingScheduleRepository.getOne(UUID.fromString(postRequestDto.getChargingScheduleId()));
        post.setChargingSchedule(chargingSchedule);

        post.setCreatedAt(new Date());

        post.setProductStatus(ProductStatus.AVAILABLE);

        postRepository.save(post);

        log.info("Post added. title:{} description:{} location:{} contact-no:{} zone:{} city:{} category:{} charging-schedule:{} start-date:{} expired-date:{} free:{}",
                post.getTitle(),post.getDescription(),post.getLocation(),post.getContactNo(),post.getZone().getName(),post.getCity().getName()
                ,post.getCategory().getName(),post.getChargingSchedule().getName(),post.getStartDate(),post.getExpireDate(),post.getIsFree()
        );

        BaseResponseDto baseResponseDto = BaseResponseDto.builder()
                .message("Post added successfully")
                .status(ResponseStatus.SUCCESS)
                .build();

        return baseResponseDto;
    }

    @Override
    public List<PostResponseDto> getAllPost(String userId) {

        User user = userRepository.getOne(UUID.fromString(userId));
        if(user == null){
            throw new ShareEverythingException("User not found.");
        }

        List<Post> posts = postRepository.findAllByUser(user);

        return null;
    }

    @Override
    public BaseResponseDto updatePost(PostRequestDto postRequestDto) {
        return null;
    }
}
