package com.example.bankdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class BankDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankDemoApplication.class, args);
    }
}
