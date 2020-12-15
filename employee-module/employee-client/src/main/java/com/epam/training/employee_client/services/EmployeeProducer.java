package com.epam.training.employee_client.services;

import com.epam.training.common_employee_api.dto.EmployeeDto;

public interface EmployeeProducer {

    void save(EmployeeDto employeeDto);

    void update(EmployeeDto employeeDto);

    void delete(EmployeeDto employeeDto);

}
