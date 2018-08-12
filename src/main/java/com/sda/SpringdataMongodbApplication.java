package com.sda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.sda.controllers")
public class SpringdataMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringdataMongodbApplication.class, args);
    }
}
