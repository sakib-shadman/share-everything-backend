package com.shareeverything.Util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ShareEverythingUtils {


    public static <T> T getDeserializedObject(String updatedData, Class<T> t) {
        T m = null;
        ObjectMapper o = new ObjectMapper();
        try {
            m = o.readValue(updatedData, t);
        } catch (JsonParseException e) {
            log.error("Exception: ", e);
        } catch (JsonMappingException e) {
            log.error("Exception: ", e);
        } catch (IOException e) {
            log.error("Exception: ", e);
        }
        return m;
    }

    public String getMonthName(Date date) {
        Format formatter = new SimpleDateFormat("MMMM");
        return formatter.format(date);
    }

    public String getYear(Date date) {
        Format formatter = new SimpleDateFormat("YYYY");
        return formatter.format(date);
    }

    public long getFirstDateOfNextMonthTimestamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date nextMonthFirstDay = calendar.getTime();
        return nextMonthFirstDay.getTime();
    }

    public String serializeMetadata(Object metadata) {

        ObjectMapper o = new ObjectMapper();
        String metadataString = "";
        try {
            metadataString = o.writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            log.error("Exception: ", e);
        }
        return metadataString;
    }


    public String getFormattedDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String getDateAsString(Date date) {

        if(date != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return "" + formatter.format(date);
        } else {
            return "";
        }

    }

    public static Date getStringAsDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        } catch (Exception e) {
            log.error("date parse failed.", e);
        }
        return new Date();
    }
}