package com.epam.training.employee_client.controllers.api.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.employee_client.controllers.api.EmployeeControllerApi;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;
import com.epam.training.employee_client.facades.EmployeeFacade;
import com.epam.training.employee_client.models.Employee;
import com.epam.training.employee_client.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeControllerApiImpl implements EmployeeControllerApi {

    private final EmployeeService employeeService;
    private final EmployeeFacade employeeFacade;

    @Override
    public EmployeeDto saveEmployee(@Valid EmployeeDto employeeDto) {
        return employeeFacade.save(employeeDto);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return employeeService.getById(id);
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentId(Long id) {
        return employeeService.getEmployeesByDepartmentId(id);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, @Valid EmployeeDto employeeDto) {
        employeeDto.setId(id);
        return employeeFacade.update(employeeDto);
    }

    @Override
    public EmployeeDto getLeaderInDepartment(Long id) {
        return employeeService.getLeaderInDepartment(id);
    }

    @Override
    public EmployeeDto transferEmployeeToAnotherDepartment(Long id, @Valid EmployeeTransferDto transferDto) {
        transferDto.setEmployeeId(id);
        return employeeFacade.transferEmployeeToAnotherDepartment(transferDto);
    }

    @Override
    public EmployeeDto dismissEmployee(Long id, @Valid DismissalEmployeeDto dismissalEmployeeDto) {
        dismissalEmployeeDto.setId(id);
        return employeeFacade.dismissEmployee(dismissalEmployeeDto);
    }

    @Override
    public EmployeeDto getEmployeeLeader(Long id) {
        return employeeService.getEmployeeLeader(id);
    }

    @Override
    public List<EmployeeDto> transferEmployeesToAnotherDepartment(@Valid EmployeesTransferDto employeesTransferDto) {
        return employeeFacade.transferEmployeesToAnotherDepartment(employeesTransferDto);
    }

    @Override
    public List<EmployeeDto> getEmployeesByField(Specification<Employee> employeeSpecification) {
        return employeeService.getEmployeesBySpecification(employeeSpecification);
    }

}
