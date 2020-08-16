package com.shareeverything;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShareEverythingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShareEverythingApplication.class, args);
    }

}
