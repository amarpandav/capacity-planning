package com.ubs.cpt.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = "com.ubs.cpt"
)
public class CptWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(CptWebApplication.class, args);
    }
}
