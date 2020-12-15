package com.epam.training.department_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = {"com.epam.training"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.epam.training.common_employee_api.api"})
@EnableCircuitBreaker
@EnableKafka
@PropertySource({"classpath:kafka.properties"})
public class DepartmentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepartmentServerApplication.class, args);
    }

}
