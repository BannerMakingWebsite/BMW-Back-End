package com.example.bmw;

import com.example.bmw.global.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BmwApplication {

    public static void main(String[] args) {
        SpringApplication.run(BmwApplication.class, args);
    }

}
