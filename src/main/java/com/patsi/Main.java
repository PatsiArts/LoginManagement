package com.patsi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.common.*", "com.patsi.*"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}