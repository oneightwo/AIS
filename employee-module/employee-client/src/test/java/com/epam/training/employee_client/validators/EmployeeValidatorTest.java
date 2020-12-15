package com.epam.training.employee_client.validators;

import com.epam.training.common_department_api.api.DepartmentResourceApi;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.employee_client.data.DepartmentTestDataProvider;
import com.epam.training.employee_client.data.EmployeeTestDataProvider;
import com.epam.training.employee_client.data.PositionTestDataProvider;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.models.Employee;
import com.epam.training.employee_client.repositories.DepartmentRepository;
import com.epam.training.employee_client.repositories.EmployeeRepository;
import feign.FeignException;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeValidatorTest {

    @Autowired
    private EmployeeValidator employeeValidator;

    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private DepartmentResourceApi departmentResourceApi;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private MapperFacade mapper;

    @Test
    void validateCreation() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        Employee employeeLeader = EmployeeTestDataProvider.getEmployeeLeader();
        when(departmentRepository.findById(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employee.getDepartment()));
        when(departmentResourceApi.getPositionById(employee.getPositionId()))
                .thenReturn(PositionTestDataProvider.getPositionDto(PositionTestDataProvider.FIRST_POSITION_ID));
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.empty());
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employeeLeader));
        when(employeeRepository.findAllByDepartmentId(employee.getDepartment().getId()))
                .thenReturn(List.of(employeeLeader));
        assertDoesNotThrow(() -> employeeValidator.validateCreation(EmployeeTestDataProvider.map(employee)));
    }

    @Test
    void validateUpdate() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        Employee employeeLeader = EmployeeTestDataProvider.getEmployeeLeader();
        when(employeeRepository.existsById(employee.getId()))
                .thenReturn(true);
        when(departmentRepository.findById(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employee.getDepartment()));
        when(departmentResourceApi.getPositionById(employee.getPositionId()))
                .thenReturn(PositionTestDataProvider.getPositionDto(PositionTestDataProvider.FIRST_POSITION_ID));
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.empty());
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employeeLeader));
        when(employeeRepository.findAllByDepartmentId(employee.getDepartment().getId()))
                .thenReturn(List.of(employeeLeader));
        assertDoesNotThrow(() -> employeeValidator.validateUpdate(EmployeeTestDataProvider.map(employee)));
    }

    @Test
    void validateTransferEmployee() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        var employeeTransferDto = new EmployeeTransferDto();
        employeeTransferDto.setEmployeeId(employee.getId());
        employeeTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID);
        Employee employeeLeader = EmployeeTestDataProvider.getEmployeeLeader();
        when(employeeRepository.existsById(employee.getId()))
                .thenReturn(true);
        when(employeeRepository.findById(employee.getId()))
                .thenReturn(Optional.of(employee));
        when(departmentRepository.findById(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)));
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.empty());
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employeeLeader));
        when(employeeRepository.findAllByDepartmentId(employee.getDepartment().getId()))
                .thenReturn(List.of(employeeLeader));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(EmployeeTestDataProvider.map(employee));
        assertDoesNotThrow(() -> employeeValidator.validateTransferEmployee(employeeTransferDto));
    }

    @Test
    void validateEmployeeWithCorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        when(employeeRepository.existsById(employee.getId()))
                .thenReturn(true);
        assertDoesNotThrow(() -> employeeValidator.validateEmployee(employee.getId()));
    }

    @Test
    void validateEmployeeWithIncorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        employee.setId(EmployeeTestDataProvider.INCORRECT_EMPLOYEE_ID);
        when(employeeRepository.existsById(employee.getId()))
                .thenReturn(false);
        assertThrows(ObjectNotFoundException.class, () -> employeeValidator.validateEmployee(employee.getId()));
    }

    @Test
    void validateDepartmentWithCorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        when(departmentRepository.findById(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employee.getDepartment()));
        assertDoesNotThrow(() -> employeeValidator.validateDepartment(employee.getDepartment().getId()));
    }

    @Test
    void validateDepartmentWithIncorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        employee.setDepartment(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
        when(departmentRepository.findById(employee.getDepartment().getId()))
                .thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class,
                () -> employeeValidator.validateDepartment(employee.getDepartment().getId()));
    }

    @Test
    void validatePositionWithCorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        when(departmentResourceApi.getPositionById(employee.getPositionId()))
                .thenReturn(PositionTestDataProvider.getPositionDto(PositionTestDataProvider.FIRST_POSITION_ID));
        assertDoesNotThrow(() -> employeeValidator.validatePosition(employee.getPositionId()));
    }

    @Test
    void validatePositionWithIncorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        employee.setPositionId(PositionTestDataProvider.INCORRECT_POSITION_ID);
        when(departmentResourceApi.getPositionById(employee.getPositionId()))
                .thenThrow(FeignException.class);
        assertThrows(ObjectNotFoundException.class, () -> employeeValidator.validatePosition(employee.getPositionId()));
    }

    @Test
    void validateEmployeeForLeaderWithCorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        employee.setIsLeader(true);
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> employeeValidator.validateEmployeeForLeader(EmployeeTestDataProvider.map(employee)));
    }

    @Test
    void validateEmployeeForLeaderIncorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        employee.setIsLeader(true);
        Employee employeeLeader = EmployeeTestDataProvider.getEmployeeLeader();
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employeeLeader));
        assertThrows(DataIntegrityViolationException.class,
                () -> employeeValidator.validateEmployeeForLeader(EmployeeTestDataProvider.map(employee)));
    }

    @Test
    void validateSalaryForEmployeeWithCorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        Employee employeeLeader = EmployeeTestDataProvider.getEmployeeLeader();
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employeeLeader));
        when(employeeRepository.findAllByDepartmentId(employee.getDepartment().getId()))
                .thenReturn(List.of(employeeLeader));
        assertDoesNotThrow(() -> employeeValidator.validateSalaryForEmployee(EmployeeTestDataProvider.map(employee)));
    }

    @Test
    void validateSalaryForEmployeeIncorrectId() {
        Employee employee = EmployeeTestDataProvider.getEmployee();
        employee.setSalary(EmployeeTestDataProvider.EMPLOYEE_LEADER_SALARY + 1);
        Employee employeeLeader = EmployeeTestDataProvider.getEmployeeLeader();
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employee.getDepartment().getId()))
                .thenReturn(Optional.of(employeeLeader));
        when(employeeRepository.findAllByDepartmentId(employee.getDepartment().getId()))
                .thenReturn(List.of(employeeLeader));
        assertThrows(DataIntegrityViolationException.class,
                () -> employeeValidator.validateSalaryForEmployee(EmployeeTestDataProvider.map(employee)));
    }

}
