package com.epam.training.employee_client.services.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.employee_client.services.EmployeeProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeProducerImpl implements EmployeeProducer {

    @Value("${kafka.topic.employee.created}")
    private String createdTopic;

    @Value("${kafka.topic.employee.updated}")
    private String updatedTopic;

    @Value("${kafka.topic.employee.deleted}")
    private String deletedTopic;

    private final KafkaTemplate<Long, EmployeeDto> kafkaTemplate;

    @Override
    public void save(EmployeeDto employeeDto) {
        log.info("(kafka) send to: " + createdTopic + " employee: " + employeeDto);
        kafkaTemplate.send(createdTopic, employeeDto.getId(), employeeDto);
    }

    @Override
    public void update(EmployeeDto employeeDto) {
        log.info("(kafka) send to: " + updatedTopic + " employee: " + employeeDto);
        kafkaTemplate.send(updatedTopic, employeeDto.getId(), employeeDto);
    }

    @Override
    public void delete(EmployeeDto employeeDto) {
        log.info("(kafka) send to: " + deletedTopic + " employee: " + employeeDto);
        kafkaTemplate.send(deletedTopic, employeeDto.getId(), employeeDto);
    }
}
