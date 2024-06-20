package com.patsi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.common.validation", "com.common.email", "com.patsi.*"})
@EntityScan({"com.common.*", "com.patsi.*"})
@EnableJpaRepositories(basePackages = {"com.common.*", "com.patsi.*"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(System.out::println);
    }
}