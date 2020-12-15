package com.epam.training.ui_server.config.mappers;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.group.id}")
    private String groupId;

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, DepartmentDto> departmentKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, DepartmentDto>();
        factory.setConsumerFactory(departmentConsumerFactory());
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Long, EmployeeDto> employeeKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<Long, EmployeeDto>();
        factory.setConsumerFactory(employeeConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, DepartmentDto> departmentConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new LongDeserializer(),
                new JsonDeserializer<>(DepartmentDto.class));
    }

    @Bean
    public ConsumerFactory<Long, EmployeeDto> employeeConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new LongDeserializer(),
                new JsonDeserializer<>(EmployeeDto.class));
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }
}
