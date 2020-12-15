package com.epam.training.ui_server.services.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.ui_server.models.EmployeeSnapshot;
import com.epam.training.ui_server.repositories.EmployeeRepository;
import com.epam.training.ui_server.services.KeycloakUserService;
import com.epam.training.ui_server.services.UiEmployeeConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UiEmployeeConsumerImpl implements UiEmployeeConsumer {

    private final EmployeeRepository employeeRepository;
    private final KeycloakUserService keycloakUserService;
    private final MapperFacade mapper;

    @KafkaListener(topics = "employee.created", groupId = "ui-security-service", containerFactory = "employeeKafkaListenerContainerFactory")
    @Override
    public void created(ConsumerRecord<Long, EmployeeDto> record) {
        EmployeeSnapshot employee = employeeRepository.save(mapper.map(record.value(), EmployeeSnapshot.class));
        keycloakUserService.createUser(employee);
        log.info("created: {}", record);
    }

    @KafkaListener(topics = "employee.updated", groupId = "ui-security-service", containerFactory = "employeeKafkaListenerContainerFactory")
    @Override
    public void updated(ConsumerRecord<Long, EmployeeDto> record) {
        employeeRepository.save(mapper.map(record.value(), EmployeeSnapshot.class));
        log.info("updated: {}", record);
    }

    @KafkaListener(topics = "employee.deleted", groupId = "ui-security-service", containerFactory = "employeeKafkaListenerContainerFactory")
    @Override
    public void deleted(ConsumerRecord<Long, EmployeeDto> record) {
        employeeRepository.save(mapper.map(record.value(), EmployeeSnapshot.class));
        log.info("deleted: {}", record);
    }
}
