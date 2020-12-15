package com.epam.training.employee_client.facades;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;

import java.util.List;

public interface EmployeeFacade {

    EmployeeDto save(EmployeeDto employeeDto);

    EmployeeDto update(EmployeeDto employeeDto);

    EmployeeDto dismissEmployee(DismissalEmployeeDto dismissalEmployeeDto);

    EmployeeDto transferEmployeeToAnotherDepartment(EmployeeTransferDto employeeTransferDto);

    List<EmployeeDto> transferEmployeesToAnotherDepartment(EmployeesTransferDto employeesTransferDto);
}
