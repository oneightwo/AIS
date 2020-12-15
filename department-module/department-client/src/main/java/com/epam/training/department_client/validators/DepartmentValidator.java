package com.epam.training.department_client.validators;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;

public interface DepartmentValidator {

    void validateCreation(DepartmentDto departmentDto);

    void validateDepartmentTransfer(DepartmentTransferDto departmentTransferDto);

    void validateDeletion(DepartmentDto departmentDto);

    void validateDepartment(Long id);

    void validateName(String name);

    void validateParent(Long parentId);
}
