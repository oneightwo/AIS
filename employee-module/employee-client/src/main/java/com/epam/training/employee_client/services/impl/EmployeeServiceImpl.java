package com.epam.training.employee_client.services.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.common_employee_api.constants.EmployeeExceptionConstants;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;
import com.epam.training.employee_client.models.DepartmentSnapshot;
import com.epam.training.employee_client.models.Employee;
import com.epam.training.employee_client.repositories.DepartmentRepository;
import com.epam.training.employee_client.repositories.EmployeeRepository;
import com.epam.training.employee_client.services.EmployeeService;
import com.epam.training.employee_client.validators.EmployeeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeValidator employeeValidator;
    private final MapperFacade mapper;

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto getById(Long employeeId) {
        return mapper.map(getEmployeeById(employeeId), EmployeeDto.class);
    }

    @Transactional
    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        employeeValidator.validateCreation(employeeDto);
        return mapper.map(employeeRepository.save(mapper.map(employeeDto, Employee.class)), EmployeeDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDto> getEmployeesByDepartmentId(Long departmentId) {
        employeeValidator.validateDepartment(departmentId);
        return mapper.mapAsList(employeeRepository.findAllByDepartmentId(departmentId), EmployeeDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto getLeaderInDepartment(Long departmentId) {
        employeeValidator.validateDepartment(departmentId);
        return mapper.map(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(departmentId)
                .orElse(null), EmployeeDto.class);
    }

    @Transactional
    @Override
    public EmployeeDto update(EmployeeDto employeeDto) {
        employeeValidator.validateUpdate(employeeDto);
        Employee employee = mapper.map(employeeDto, Employee.class);
        return mapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Transactional
    @Override
    public EmployeeDto transferEmployeeToAnotherDepartment(EmployeeTransferDto employeeTransferDto) {
        employeeValidator.validateTransferEmployee(employeeTransferDto);
        Employee employee = getEmployeeById(employeeTransferDto.getEmployeeId());
        employee.setDepartment(departmentRepository.findById(employeeTransferDto.getDestinationDepartmentId()).get());
        return mapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Transactional
    @Override
    public EmployeeDto dismissEmployee(DismissalEmployeeDto dismissalEmployeeDto) {
        Employee employee = getEmployeeById(dismissalEmployeeDto.getId());
        employee.setDateOfDismissal(dismissalEmployeeDto.getDateOfDismissal());
        return mapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDto getEmployeeLeader(Long employeeId) {
        Employee employee = getEmployeeById(employeeId);
        Employee leader = employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId())
                .orElse(null);
        return mapper.map(leader, EmployeeDto.class);
    }

    @Transactional
    @Override
    public List<EmployeeDto> transferEmployeesToAnotherDepartment(EmployeesTransferDto employeesTransferDto) {
        employeeValidator.validateDepartment(employeesTransferDto.getDestinationDepartmentId());
        employeeValidator.validateDepartment(employeesTransferDto.getCurrentDepartmentId());
        List<Employee> employees = employeeRepository.findAllByDepartmentId(employeesTransferDto.getCurrentDepartmentId());
        employees.forEach(employee ->
                employee.setDepartment(mapper.map(departmentRepository.findById(employeesTransferDto.getDestinationDepartmentId()).get(),
                        DepartmentSnapshot.class)));
        return mapper.mapAsList(employeeRepository.saveAll(employees), EmployeeDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeDto> getEmployeesBySpecification(Specification<Employee> employeeSpecification) {
        return mapper.mapAsList(employeeRepository.findAll(employeeSpecification), EmployeeDto.class);
    }

    private Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ObjectNotFoundException(EmployeeExceptionConstants.EMPLOYEE_WITH_THIS_ID_DOES_NOT_EXIST));
    }

}
