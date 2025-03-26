package com.ss.excel.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ss")
public class ExcelProcessing {

    public static void main(String[] args) {
        SpringApplication.run(ExcelProcessing.class, args);
    }
}