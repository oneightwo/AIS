package com.epam.training.department_client.services.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.department_client.models.EmployeeSnapshot;
import com.epam.training.department_client.repositories.EmployeeRepository;
import com.epam.training.department_client.services.DepartmentConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentConsumerImpl implements DepartmentConsumer {

    private final EmployeeRepository employeeRepository;
    private final MapperFacade mapper;

    @KafkaListener(topics = "employee.created", groupId = "department-service")
    @Override
    public void created(ConsumerRecord<Long, EmployeeDto> record) {
        employeeRepository.save(mapper.map(record.value(), EmployeeSnapshot.class));
        log.info("created: {}", record);
    }

    @KafkaListener(topics = "employee.updated", groupId = "department-service")
    @Override
    public void updated(ConsumerRecord<Long, EmployeeDto> record) {
        employeeRepository.save(mapper.map(record.value(), EmployeeSnapshot.class));
        log.info("updated: {}", record);
    }

    @KafkaListener(topics = "employee.deleted", groupId = "department-service")
    @Override
    public void deleted(ConsumerRecord<Long, EmployeeDto> record) {
        employeeRepository.deleteById(record.value().getId());
        log.info("deleted: {}", record);
    }
}
