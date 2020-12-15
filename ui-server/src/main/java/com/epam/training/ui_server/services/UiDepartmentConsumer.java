package com.epam.training.ui_server.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface UiDepartmentConsumer {

    void created(ConsumerRecord<Long, DepartmentDto> record);

    void updated(ConsumerRecord<Long, DepartmentDto> record);

    void deleted(ConsumerRecord<Long, DepartmentDto> record);

}
