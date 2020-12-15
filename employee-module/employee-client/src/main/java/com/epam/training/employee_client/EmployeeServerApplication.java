package com.epam.training.employee_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.epam.training")
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.epam.training.common_department_api.api"})
@EnableCircuitBreaker
@EnableKafka
@PropertySource("classpath:kafka.properties")
public class EmployeeServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServerApplication.class, args);
    }
}
