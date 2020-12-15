package com.epam.training.department_client.validators.impl;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.common_department_api.constants.DepartmentExceptionConstants;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.department_client.repositories.DepartmentRepository;
import com.epam.training.department_client.repositories.EmployeeRepository;
import com.epam.training.department_client.validators.DepartmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentValidatorImpl implements DepartmentValidator {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void validateCreation(DepartmentDto departmentDto) {
        validateName(departmentDto.getName());
        validateParent(departmentDto.getParentId());
    }

    @Override
    public void validateDepartmentTransfer(DepartmentTransferDto departmentTransferDto) {
        Long currentDepartmentId = departmentTransferDto.getCurrentDepartmentId();
        Long destinationDepartmentId = departmentTransferDto.getDestinationDepartmentId();
        if (currentDepartmentId.equals(destinationDepartmentId)) {
            throw new DataIntegrityViolationException(DepartmentExceptionConstants.DEPARTMENT_ID_TO_MUST_NOT_BE_EQUAL_TO_DEPARTMENT_ID_FROM);
        }
        try {
            validateDepartment(currentDepartmentId);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(DepartmentExceptionConstants.DEPARTMENT_CURRENT_DEPARTMENT_ID_WITH_THIS_ID_DOES_NOT_EXIST);
        }
        try {
            validateDepartment(destinationDepartmentId);
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException(DepartmentExceptionConstants.DEPARTMENT_DESTINATION_DEPARTMENT_ID_WITH_THIS_ID_DOES_NOT_EXIST);
        }
    }

    @Override
    public void validateDeletion(DepartmentDto departmentDto) {
        if (!employeeRepository.findAllByDepartmentId(departmentDto.getId()).isEmpty()) {
            throw new DataIntegrityViolationException(DepartmentExceptionConstants.REMOVAL_IS_NOT_POSSIBLE_THE_DEPARTMENT_HAS_EMPLOYEES);
        }
        if (!departmentDto.getChildren().isEmpty()) {
            throw new DataIntegrityViolationException(DepartmentExceptionConstants.THE_DEPARTMENT_HAS_DEPENDENT_DEPARTMENTS);
        }
    }

    @Override
    public void validateDepartment(Long id) {
        if (Objects.isNull(id) || departmentRepository.findById(id).isEmpty()) {
            throw new ObjectNotFoundException(DepartmentExceptionConstants.DEPARTMENT_WITH_THIS_ID_DOES_NOT_EXIST);
        }
    }

    @Override
    public void validateName(String name) {
        if (departmentRepository.findByName(name).isPresent()) {
            throw new DataIntegrityViolationException(DepartmentExceptionConstants.DEPARTMENT_WITH_THE_SAME_NAME_ALREADY_EXISTS);
        }
    }

    @Override
    public void validateParent(Long parentId) {
        if (Objects.nonNull(parentId) && departmentRepository.findById(parentId).isEmpty()) {
            throw new DataIntegrityViolationException(DepartmentExceptionConstants.DEPARTMENT_WITH_THIS_ID_DOES_NOT_EXIST);
        }
    }
}
