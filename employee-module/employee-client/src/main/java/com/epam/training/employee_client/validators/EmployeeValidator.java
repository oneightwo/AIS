package com.epam.training.employee_client.validators;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.models.Employee;

public interface EmployeeValidator {

    void validateCreation(EmployeeDto employeeDto);

    void validateUpdate(EmployeeDto employeeDto);

    void validateEmployee(long employeeId);

    void validateDepartment(long departmentId);

    void validatePosition(long positionId);

    void validateTransferEmployee(EmployeeTransferDto employeeTransferDto);

    void validateEmployeeForLeader(EmployeeDto employeeDto);

    void validateSalaryForEmployee(EmployeeDto employeeDto);
}
