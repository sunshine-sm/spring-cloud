package com.shaoming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

// 开启 EurekaClient 注解，如果配置了Eureka注册中心，默认会开启该注解
@EnableEurekaClient
@SpringBootApplication
public class ServerProvider01Application {

    public static void main(String[] args) {
        SpringApplication.run(ServerProvider01Application.class, args);
    }

}
