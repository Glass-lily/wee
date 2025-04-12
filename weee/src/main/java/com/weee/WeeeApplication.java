package com.weee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weee.mapper")
public class WeeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeeeApplication.class, args);
    }

}
