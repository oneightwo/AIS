package com.epam.training.employee_client.services;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.services.CRUDService;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;
import com.epam.training.employee_client.models.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EmployeeService extends CRUDService<EmployeeDto, Long> {

    List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId);

    EmployeeDto getLeaderInDepartment(Long departmentId);

    EmployeeDto transferEmployeeToAnotherDepartment(EmployeeTransferDto moveEmployeeDto);

    EmployeeDto dismissEmployee(DismissalEmployeeDto dismissalEmployeeDto);

    EmployeeDto getEmployeeLeader(Long employeeId);

    List<EmployeeDto> transferEmployeesToAnotherDepartment(EmployeesTransferDto employeesTransferDto);

    List<EmployeeDto> getEmployeesBySpecification(Specification<Employee> employeeSpecification);
}
