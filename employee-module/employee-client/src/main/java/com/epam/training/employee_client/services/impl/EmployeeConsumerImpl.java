package com.epam.training.employee_client.services.impl;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.employee_client.models.DepartmentSnapshot;
import com.epam.training.employee_client.repositories.DepartmentRepository;
import com.epam.training.employee_client.services.EmployeeConsumer;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeConsumerImpl implements EmployeeConsumer {

    private final DepartmentRepository departmentRepository;
    private final MapperFacade mapper;

    @KafkaListener(topics = "department.created", groupId = "employee-service")
    @Override
    public void created(ConsumerRecord<Long, DepartmentDto> record) {
        departmentRepository.save(mapper.map(record.value(), DepartmentSnapshot.class));
    }

    @KafkaListener(topics = "department.updated", groupId = "employee-service")
    @Override
    public void updated(ConsumerRecord<Long, DepartmentDto> record) {
        departmentRepository.save(mapper.map(record.value(), DepartmentSnapshot.class));
    }

    @KafkaListener(topics = "department.deleted", groupId = "employee-service")
    @Override
    public void deleted(ConsumerRecord<Long, DepartmentDto> record) {
        departmentRepository.delete(mapper.map(record.value(), DepartmentSnapshot.class));
    }
}
