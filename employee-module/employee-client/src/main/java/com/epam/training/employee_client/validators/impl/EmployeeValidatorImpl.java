package com.epam.training.employee_client.validators.impl;

import com.epam.training.common_department_api.api.DepartmentResourceApi;
import com.epam.training.common_department_api.constants.DepartmentExceptionConstants;
import com.epam.training.common_department_api.constants.PositionExceptionConstants;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.common_employee_api.constants.EmployeeExceptionConstants;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.models.Employee;
import com.epam.training.employee_client.repositories.DepartmentRepository;
import com.epam.training.employee_client.repositories.EmployeeRepository;
import com.epam.training.employee_client.validators.EmployeeValidator;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeValidatorImpl implements EmployeeValidator {

    private final EmployeeRepository employeeRepository;
    private final DepartmentResourceApi departmentResourceApi;
    private final DepartmentRepository departmentRepository;
    private final MapperFacade mapper;

    @Override
    public void validateCreation(EmployeeDto employeeDto) {
        validateDepartment(employeeDto.getDepartmentId());
        validatePosition(employeeDto.getPositionId());
        validateEmployeeForLeader(employeeDto);
        validateSalaryForEmployee(employeeDto);
    }

    @Override
    public void validateUpdate(EmployeeDto employeeDto) {
        validateEmployee(employeeDto.getId());
        validateDepartment(employeeDto.getDepartmentId());
        validatePosition(employeeDto.getPositionId());
        validateEmployeeForLeader(employeeDto);
        validateSalaryForEmployee(employeeDto);
    }

    @Override
    public void validateEmployee(long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ObjectNotFoundException(EmployeeExceptionConstants.EMPLOYEE_WITH_THIS_ID_DOES_NOT_EXIST);
        }
    }

    @Override
    public void validateDepartment(long departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ObjectNotFoundException(DepartmentExceptionConstants.DEPARTMENT_WITH_THIS_ID_DOES_NOT_EXIST));
    }

    @Override
    public void validatePosition(long positionId) {
        try {
            departmentResourceApi.getPositionById(positionId);
        } catch (FeignException e) {
            throw new ObjectNotFoundException(PositionExceptionConstants.POSITION_WITH_THIS_ID_DOES_NOT_EXIST);
        }
    }

    @Override
    public void validateTransferEmployee(EmployeeTransferDto employeeTransferDto) {
        validateEmployee(employeeTransferDto.getEmployeeId());
        validateDepartment(employeeTransferDto.getDestinationDepartmentId());
        EmployeeDto employeeDto = mapper.map(employeeRepository.findById(employeeTransferDto.getEmployeeId()).get(), EmployeeDto.class);
        employeeDto.setDepartmentId(employeeTransferDto.getDestinationDepartmentId());
        validateEmployeeForLeader(employeeDto);
        validateSalaryForEmployee(employeeDto);
    }

    @Override
    public void validateEmployeeForLeader(EmployeeDto employeeDto) {
        Employee leader = employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employeeDto.getDepartmentId())
                .orElse(null);
        if (Objects.nonNull(leader) && !leader.getId().equals(employeeDto.getId()) && employeeDto.getIsLeader()) {
            throw new DataIntegrityViolationException(EmployeeExceptionConstants.THE_DEPARTMENT_ALREADY_HAS_A_LEADER);
        }
    }

    @Override
    public void validateSalaryForEmployee(EmployeeDto employeeDto) {
        Employee leader = employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employeeDto.getDepartmentId())
                .orElse(null);
        List<Employee> employees = employeeRepository.findAllByDepartmentId(employeeDto.getDepartmentId());

        if (Objects.nonNull(leader)) {
            if (employeeDto.getIsLeader() && employees.stream()
                    .anyMatch(employee -> !employee.getId().equals(employeeDto.getId())
                            && employee.getSalary() > employeeDto.getSalary())) {
                throw new DataIntegrityViolationException(EmployeeExceptionConstants.THE_BOSS_SALARY_CANNOT_BE_LESS_THAN_THE_EMPLOYEE_SALARY);
            }
            if (!employeeDto.getIsLeader() && employeeDto.getSalary() > leader.getSalary() && !leader.getId().equals(employeeDto.getId())) {
                throw new DataIntegrityViolationException(EmployeeExceptionConstants.THE_EMPLOYEE_SALARY_CANNOT_BE_MORE_THAN_THE_BOSS_SALARY);
            }
        } else {
            Employee employee = employees.stream().max(Comparator.comparing(Employee::getSalary)).orElse(null);
            if (Objects.nonNull(employee) && employeeDto.getIsLeader() && employee.getSalary() > employeeDto.getSalary()) {
                throw new DataIntegrityViolationException(EmployeeExceptionConstants.THE_BOSS_SALARY_CANNOT_BE_LESS_THAN_THE_EMPLOYEE_SALARY);
            }
        }
    }
}
