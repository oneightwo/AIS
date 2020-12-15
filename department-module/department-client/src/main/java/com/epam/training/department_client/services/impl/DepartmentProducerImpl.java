package com.epam.training.department_client.services.impl;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.department_client.services.DepartmentProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentProducerImpl implements DepartmentProducer {

    @Value("${kafka.topic.department.created}")
    private String createdTopic;

    @Value("${kafka.topic.department.updated}")
    private String updatedTopic;

    @Value("${kafka.topic.department.deleted}")
    private String deletedTopic;

    private final KafkaTemplate<Long, DepartmentDto> kafkaTemplate;

    @Override
    public void created(DepartmentDto departmentDto) {
        log.info("(kafka) send to: " + createdTopic + " department: " + departmentDto);
        kafkaTemplate.send(createdTopic, departmentDto.getId(), departmentDto);
    }

    @Override
    public void updated(DepartmentDto departmentDto) {
        log.info("(kafka) send to: " + updatedTopic + " department: " + departmentDto);
        kafkaTemplate.send(updatedTopic, departmentDto.getId(), departmentDto);
    }

    @Override
    public void deleted(DepartmentDto departmentDto) {
        log.info("(kafka) send to: " + deletedTopic + " department: " + departmentDto);
        kafkaTemplate.send(deletedTopic, departmentDto.getId(), departmentDto);
    }
}
