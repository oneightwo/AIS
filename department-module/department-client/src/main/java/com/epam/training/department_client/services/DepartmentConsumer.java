package com.epam.training.department_client.services;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface DepartmentConsumer {

    void created(ConsumerRecord<Long, EmployeeDto> record);

    void updated(ConsumerRecord<Long, EmployeeDto> record);

    void deleted(ConsumerRecord<Long, EmployeeDto> record);

}
