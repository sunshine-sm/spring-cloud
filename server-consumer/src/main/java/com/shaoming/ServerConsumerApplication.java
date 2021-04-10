package com.shaoming;

import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServerConsumerApplication {

//    /**
//     * 设置全局负载均衡策略【随机策略】
//     */
//    @Bean
//    public RandomRule randomRule() {
//        return new RandomRule();
//    }

    @Bean
//    @LoadBalanced // 负载均衡注解
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ServerConsumerApplication.class, args);
    }

}
