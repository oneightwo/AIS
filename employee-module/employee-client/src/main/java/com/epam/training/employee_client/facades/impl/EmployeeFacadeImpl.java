package com.epam.training.employee_client.facades.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;
import com.epam.training.employee_client.facades.EmployeeFacade;
import com.epam.training.employee_client.services.EmployeeProducer;
import com.epam.training.employee_client.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeFacadeImpl implements EmployeeFacade {

    private final EmployeeService employeeService;
    private final EmployeeProducer employeeProducer;

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        EmployeeDto savedEmployee = employeeService.save(employeeDto);
        employeeProducer.save(savedEmployee);
        return savedEmployee;
    }

    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.update(employeeDto);
        employeeProducer.update(updatedEmployee);
        return updatedEmployee;
    }

    @Override
    public EmployeeDto dismissEmployee(DismissalEmployeeDto dismissalEmployeeDto) {
        EmployeeDto employeeDto = employeeService.dismissEmployee(dismissalEmployeeDto);
        employeeProducer.delete(employeeDto);
        return employeeDto;
    }

    @Override
    public EmployeeDto transferEmployeeToAnotherDepartment(EmployeeTransferDto employeeTransferDto) {
        EmployeeDto employeeDto = employeeService.transferEmployeeToAnotherDepartment(employeeTransferDto);
        employeeProducer.update(employeeDto);
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> transferEmployeesToAnotherDepartment(EmployeesTransferDto employeesTransferDto) {
        List<EmployeeDto> employeeDtoList = employeeService.transferEmployeesToAnotherDepartment(employeesTransferDto);
        employeeDtoList.forEach(employeeProducer::update);
        return employeeDtoList;
    }

}
