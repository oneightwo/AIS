package com.epam.training.employee_client.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.employee_client.data.DepartmentTestDataProvider;
import com.epam.training.employee_client.data.EmployeeTestDataProvider;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;
import com.epam.training.employee_client.models.DepartmentSnapshot;
import com.epam.training.employee_client.models.Employee;
import com.epam.training.employee_client.repositories.DepartmentRepository;
import com.epam.training.employee_client.repositories.EmployeeRepository;
import com.epam.training.employee_client.validators.EmployeeValidator;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployeeValidator employeeValidator;
    @MockBean
    private MapperFacade mapper;

    @Test
    void getByIdWithCorrectId() {
        EmployeeDto employeeDto = getDataForGetById();
        assertEquals(employeeDto, employeeService.getById(employeeDto.getId()));
    }

    @Test
    void getByIdWithIncorrectId() {
        getDataForGetById();
        assertThrows(ObjectNotFoundException.class,
                () -> employeeService.getById(EmployeeTestDataProvider.INCORRECT_EMPLOYEE_ID));
    }

    private EmployeeDto getDataForGetById() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        when(employeeRepository.findById(employeeDto.getId()))
                .thenReturn(Optional.of(employee));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        return employeeDto;
    }

    @Test
    void save() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        assertEquals(employeeDto, employeeService.save(employeeDto));
    }

    @Test
    void getEmployeesByDepartmentIdWithCorrectId() {
        EmployeeDto employeeDto = getDataForEmployeesByDepartmentId();
        when(mapper.mapAsList(anyList(), eq(EmployeeDto.class)))
                .thenReturn(List.of(employeeDto));
        assertEquals(List.of(employeeDto), employeeService.getEmployeesByDepartmentId(employeeDto.getDepartmentId()));
    }

    @Test
    void getEmployeesByDepartmentIdWithIncorrectId() {
        getDataForEmployeesByDepartmentId();
        when(mapper.mapAsList(anyList(), eq(EmployeeDto.class)))
                .thenReturn(Collections.emptyList());
        assertEquals(Collections.emptyList(),
                employeeService.getEmployeesByDepartmentId(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
    }

    private EmployeeDto getDataForEmployeesByDepartmentId() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        when(employeeRepository.findAllByDepartmentId(employeeDto.getDepartmentId()))
                .thenReturn(List.of(employee));
        return employeeDto;
    }

    @Test
    void getLeaderInDepartment() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employeeDto.getDepartmentId()))
                .thenReturn(Optional.of(employee));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        assertEquals(employeeDto,
                employeeService.getLeaderInDepartment(employeeDto.getDepartmentId()));
    }

    @Test
    void update() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        assertEquals(employeeDto, employeeService.update(employeeDto));
    }

    @Test
    void transferEmployeeToAnotherDepartmentWithCorrectId() {
        var employeeTransferDto = new EmployeeTransferDto();
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        employeeTransferDto.setEmployeeId(EmployeeTestDataProvider.EMPLOYEE_ID);
        employeeTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(employeeRepository.findById(employeeTransferDto.getEmployeeId()))
                .thenReturn(Optional.of(employee));
        when(departmentRepository.findById(employeeTransferDto.getDestinationDepartmentId()))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        assertEquals(employeeDto, employeeService.transferEmployeeToAnotherDepartment(employeeTransferDto));
    }

    @Test
    void transferEmployeeToAnotherDepartmentWithIncorrectId() {
        var employeeTransferDto = new EmployeeTransferDto();
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        employeeTransferDto.setEmployeeId(EmployeeTestDataProvider.EMPLOYEE_ID);
        employeeTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(employeeRepository.findById(employeeTransferDto.getEmployeeId()))
                .thenReturn(Optional.empty());
        when(departmentRepository.findById(employeeTransferDto.getDestinationDepartmentId()))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        assertThrows(ObjectNotFoundException.class,
                () -> employeeService.transferEmployeeToAnotherDepartment(employeeTransferDto));
    }

    @Test
    void dismissEmployeeWithCorrectId() {
        DismissalEmployeeDto dismissalEmployeeDto = EmployeeTestDataProvider.getDismissalEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(dismissalEmployeeDto);
        EmployeeDto employeeDto = EmployeeTestDataProvider.map(employee);
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        when(employeeRepository.findById(dismissalEmployeeDto.getId()))
                .thenReturn(Optional.of(employee));
        assertEquals(employeeDto, employeeService.dismissEmployee(dismissalEmployeeDto));
        assertNotNull(employeeService.dismissEmployee(dismissalEmployeeDto).getDateOfDismissal());
    }

    @Test
    void dismissEmployeeWithIncorrectId() {
        DismissalEmployeeDto dismissalEmployeeDto = EmployeeTestDataProvider.getDismissalEmployeeDto();
        Employee employee = EmployeeTestDataProvider.map(dismissalEmployeeDto);
        EmployeeDto employeeDto = EmployeeTestDataProvider.map(employee);
        dismissalEmployeeDto.setId(EmployeeTestDataProvider.INCORRECT_EMPLOYEE_ID);
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeDto);
        when(employeeRepository.findById(dismissalEmployeeDto.getId()))
                .thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> employeeService.dismissEmployee(dismissalEmployeeDto));
    }

    @Test
    void getEmployeeLeaderWithCorrectId() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        EmployeeDto employeeLeaderDto = EmployeeTestDataProvider.getEmployeeLeaderDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        Employee employeeLeader = EmployeeTestDataProvider.map(employeeLeaderDto);
        when(employeeRepository.findById(employeeDto.getId()))
                .thenReturn(Optional.of(employee));
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employeeDto.getDepartmentId()))
                .thenReturn(Optional.of(employeeLeader));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeLeaderDto);
        assertEquals(employeeLeaderDto,
                employeeService.getEmployeeLeader(employeeDto.getId()));
    }

    @Test
    void getEmployeeLeaderWithIncorrectId() {
        EmployeeDto employeeDto = EmployeeTestDataProvider.getEmployeeDto();
        EmployeeDto employeeLeaderDto = EmployeeTestDataProvider.getEmployeeLeaderDto();
        Employee employee = EmployeeTestDataProvider.map(employeeDto);
        Employee employeeLeader = EmployeeTestDataProvider.map(employeeLeaderDto);
        when(employeeRepository.findById(employeeDto.getId()))
                .thenReturn(Optional.of(employee));
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(employeeDto.getDepartmentId()))
                .thenReturn(Optional.of(employeeLeader));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeLeaderDto);
        assertThrows(ObjectNotFoundException.class,
                () -> employeeService.getEmployeeLeader(EmployeeTestDataProvider.INCORRECT_EMPLOYEE_ID));
    }

    @Test
    void transferEmployeesToAnotherDepartment() {
        var employeesTransferDto = new EmployeesTransferDto();
        employeesTransferDto.setCurrentDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        employeesTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID);
        Employee employee = EmployeeTestDataProvider.getEmployee();
        EmployeeDto resultEmployeeDto = EmployeeTestDataProvider.map(EmployeeTestDataProvider.getEmployee());
        when(employeeRepository.findAllByDepartmentId(employeesTransferDto.getCurrentDepartmentId()))
                .thenReturn(List.of(employee));
        DepartmentSnapshot destinationDepartment = DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(employeesTransferDto.getCurrentDepartmentId());
        when(departmentRepository.findById(employeesTransferDto.getDestinationDepartmentId()))
                .thenReturn(Optional.of(destinationDepartment));
        when(mapper.mapAsList(anyList(), eq(EmployeeDto.class)))
                .thenReturn(List.of(resultEmployeeDto));
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(DepartmentTestDataProvider.map(destinationDepartment));
        assertEquals(List.of(resultEmployeeDto), employeeService.transferEmployeesToAnotherDepartment(employeesTransferDto));
    }
}

