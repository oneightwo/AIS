package com.epam.training.department_client.services;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.services.CRUDService;

import java.util.List;

public interface EmployeeService extends CRUDService<EmployeeDto, Long> {

    List<EmployeeDto> getAll();

}
