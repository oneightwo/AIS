package com.epam.training.ui_server.services.impl;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.ui_server.models.DepartmentSnapshot;
import com.epam.training.ui_server.repositories.DepartmentRepository;
import com.epam.training.ui_server.services.UiDepartmentConsumer;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UiDepartmentConsumerImpl implements UiDepartmentConsumer {

    private final DepartmentRepository departmentRepository;
    private final MapperFacade mapper;

    @KafkaListener(topics = "department.created", groupId = "ui-security-service", containerFactory = "departmentKafkaListenerContainerFactory")
    @Override
    public void created(ConsumerRecord<Long, DepartmentDto> record) {
        DepartmentSnapshot map = mapper.map(record.value(), DepartmentSnapshot.class);
        departmentRepository.save(map);
    }

    @KafkaListener(topics = "department.updated", groupId = "ui-security-service", containerFactory = "departmentKafkaListenerContainerFactory")
    @Override
    public void updated(ConsumerRecord<Long, DepartmentDto> record) {
        departmentRepository.save(mapper.map(record.value(), DepartmentSnapshot.class));
    }

    @KafkaListener(topics = "department.deleted", groupId = "ui-security-service", containerFactory = "departmentKafkaListenerContainerFactory")
    @Override
    public void deleted(ConsumerRecord<Long, DepartmentDto> record) {
        departmentRepository.delete(mapper.map(record.value(), DepartmentSnapshot.class));
    }
}
