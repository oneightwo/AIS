package com.epam.training.ui_server.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.ui_server.dto.UiDepartmentInfo;
import com.epam.training.ui_server.dto.UiEmployee;

import java.io.File;
import java.security.Principal;
import java.util.List;

public interface UserService {

    UiEmployee getUser(Principal principal);

    String getUserRole(Principal principal);

    DepartmentDto getCurrentDepartment(Principal principal);

    UiDepartmentInfo getCurrentDepartmentInfo(Principal principal);

    List<UiDepartmentInfo> getChildrenDepartmentsInfo(Principal principal);

    List<UiEmployee> getEmployees(Principal principal);

    EmployeeDto getEmployeeById(Long id);

    List<DepartmentDto> getDepartmentsAvailableForCurrentPrincipal(Principal principal);

    List<PositionDto> getPositions();

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    void dismissalEmployee(DismissalEmployeeDto dismissalEmployeeDto);

    DepartmentDto createDepartment(DepartmentDto departmentDto);

    DepartmentDto updateDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentById(Long departmentId);

    void deleteDepartment(Long departmentId);

    File createReportByDepartments(Principal principal);

    File createReportByEmployees(Principal principal);
}
