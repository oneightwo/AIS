package com.epam.training.department_client.facades;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;

public interface DepartmentFacade {

    DepartmentDto rename(DepartmentDto departmentDto);

    DepartmentDto save(DepartmentDto departmentDto);

    DepartmentDto departmentTransfer(DepartmentTransferDto departmentTransferDto);

    void delete(Long departmentId);
}
