package com.api.rest.canvas2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Canvas2Application {

    public static void main(String[] args) {
        SpringApplication.run(Canvas2Application.class, args);
    }

}
