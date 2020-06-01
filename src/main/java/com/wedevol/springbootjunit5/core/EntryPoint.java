package com.wedevol.springbootjunit5.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class EntryPoint extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }

}
