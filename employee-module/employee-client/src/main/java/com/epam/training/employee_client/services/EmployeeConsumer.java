package com.epam.training.employee_client.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface EmployeeConsumer {

    void created(ConsumerRecord<Long, DepartmentDto> record);

    void updated(ConsumerRecord<Long, DepartmentDto> record);

    void deleted(ConsumerRecord<Long, DepartmentDto> record);

}
