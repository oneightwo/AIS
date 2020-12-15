package com.epam.training.department_client.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface DepartmentProducer {

    void created(DepartmentDto departmentDto);

    void updated(DepartmentDto departmentDto);

    void deleted(DepartmentDto departmentDto);

}
