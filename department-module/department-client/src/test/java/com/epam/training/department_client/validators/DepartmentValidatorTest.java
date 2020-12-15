package com.epam.training.department_client.validators;

import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.department_client.data.DepartmentTestDataProvider;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.department_client.models.Department;
import com.epam.training.department_client.repositories.DepartmentRepository;
import com.epam.training.department_client.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DepartmentValidatorTest {

    @Autowired
    private DepartmentValidator departmentValidator;

    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void validateCreationWithoutExistDepartmentWithSameName() {
        Department department = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findByName(department.getName()))
                .thenReturn(Optional.empty());
        when(departmentRepository.findById(department.getParent().getId()))
                .thenReturn(Optional.of(department.getParent()));
        assertDoesNotThrow(() -> departmentValidator.validateCreation(DepartmentTestDataProvider.map(department)));
    }

    @Test
    void validateCreationWithExistDepartmentWithSameName() {
        Department department = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findByName(department.getName()))
                .thenReturn(Optional.of(department));
        when(departmentRepository.findById(department.getParent().getId()))
                .thenReturn(Optional.of(department.getParent()));
        assertThrows(DataIntegrityViolationException.class,
                () -> departmentValidator.validateCreation(DepartmentTestDataProvider.map(department)));
    }

    @Test
    void validateDepartmentTransferWithDifferentIds() {
        var departmentTransferDto = new DepartmentTransferDto();
        departmentTransferDto.setCurrentDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        departmentTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID);
        when(departmentRepository.findById(departmentTransferDto.getCurrentDepartmentId()))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(departmentRepository.findById(departmentTransferDto.getDestinationDepartmentId()))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)));
        assertDoesNotThrow(() -> departmentValidator.validateDepartmentTransfer(departmentTransferDto));
    }

    @Test
    void validateDepartmentTransferWithSameIds() {
        var departmentTransferDto = new DepartmentTransferDto();
        departmentTransferDto.setCurrentDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        departmentTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(departmentTransferDto.getCurrentDepartmentId()))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(departmentRepository.findById(departmentTransferDto.getDestinationDepartmentId()))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)));
        assertThrows(DataIntegrityViolationException.class,
                () -> departmentValidator.validateDepartmentTransfer(departmentTransferDto));
    }

    @Test
    void validateDeletionWhenDepartmentWithoutChildren() {
        var department = DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(employeeRepository.findAllByDepartmentId(department.getId()))
                .thenReturn(Collections.emptyList());
        assertDoesNotThrow(() -> departmentValidator.validateDeletion(DepartmentTestDataProvider.map(department)));
    }

    @Test
    void validateDeletionWhenDepartmentWithChildren() {
        var department = DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(employeeRepository.findAllByDepartmentId(department.getId()))
                .thenReturn(Collections.emptyList());
        assertThrows(DataIntegrityViolationException.class,
                () -> departmentValidator.validateDeletion(DepartmentTestDataProvider.map(department)));
    }

    @Test
    void validateDepartmentWhenDepartmentExists() {
        Department department = DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(department.getId()))
                .thenReturn(Optional.of(department));
        assertDoesNotThrow(() -> departmentValidator.validateDepartment(department.getId()));
    }

    @Test
    void validateDepartmentWhenDepartmentDoesNotExist() {
        Department department = DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(department.getId()))
                .thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class,
                () -> departmentValidator.validateDepartment(department.getId()));
    }

    @Test
    void validateNameWhenDepartmentDoesNotExist() {
        Department department = DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findByName(department.getName()))
                .thenReturn(Optional.empty());
        assertDoesNotThrow(() -> departmentValidator.validateName(department.getName()));
    }

    @Test
    void validateNameWhenDepartmentAlreadyExists() {
        Department department = DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findByName(department.getName()))
                .thenReturn(Optional.of(department));
        assertThrows(DataIntegrityViolationException.class,
                () -> departmentValidator.validateName(department.getName()));
    }

    @Test
    void validateParentWhenItExists() {
        Department department = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(department.getParent().getId()))
                .thenReturn(Optional.of(department.getParent()));
        assertDoesNotThrow(() -> departmentValidator.validateParent(department.getParent().getId()));
    }

    @Test
    void validateParentWhenItDoesNotExist() {
        Department department = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(department.getParent().getId()))
                .thenReturn(Optional.empty());
        assertThrows(DataIntegrityViolationException.class,
                () -> departmentValidator.validateParent(department.getParent().getId()));
    }
}
