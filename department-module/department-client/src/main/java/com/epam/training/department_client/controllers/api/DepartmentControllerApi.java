package com.epam.training.department_client.controllers.api;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.department_client.dto.TotalEmployeeSalary;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface DepartmentControllerApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DepartmentDto createDepartment(@RequestBody @Valid DepartmentDto departmentDto);

    @PutMapping("/{id}")
    DepartmentDto renameDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentDto departmentDto);

    @GetMapping("/{id}")
    DepartmentDto getDepartmentById(@PathVariable Long id);

    @GetMapping("/{id}/info")
    DepartmentInformationDto getDepartmentInfoById(@PathVariable Long id);

    @GetMapping
    DepartmentDto getDepartmentByName(@RequestParam String name);

    @PutMapping("/{id}/transfer")
    DepartmentDto transferDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentTransferDto departmentTransferDto);

    @GetMapping("/{id}/children/current")
    List<DepartmentDto> getCurrentChildren(@PathVariable Long id);

    @GetMapping("/{id}/parents")
    List<DepartmentDto> getParentsHierarchy(@PathVariable Long id);

    @GetMapping("/{id}/children")
    List<DepartmentDto> getChildrenHierarchy(@PathVariable Long id);

    @GetMapping("/positions")
    List<PositionDto> getAllPositions();

    @GetMapping("/positions/{id}")
    PositionDto getPositionById(@PathVariable Long id);

    @GetMapping("/{id}/salaries")
    TotalEmployeeSalary getEmployeeSalaryByDepartmentId(@PathVariable Long id);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id);

}
