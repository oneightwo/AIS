package com.epam.training.department_client.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.department_client.dto.TotalEmployeeSalary;
import com.epam.training.core_common_api.services.CRUDService;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;

import java.util.List;

public interface DepartmentService extends CRUDService<DepartmentDto, Long> {

    DepartmentInformationDto getInformationById(Long departmentId);

    DepartmentDto rename(DepartmentDto departmentDto);

    List<DepartmentDto> getParentsHierarchy(Long departmentId);

    List<DepartmentDto> getChildrenHierarchy(Long departmentId);

    List<DepartmentDto> getCurrentChildren(Long departmentId);

    DepartmentDto getByName(String name);

    DepartmentDto departmentTransfer(DepartmentTransferDto departmentTransferDto);

    DepartmentDto getParent(Long departmentId);

    TotalEmployeeSalary getEmployeeSalaryByDepartmentId(Long departmentId);
}
