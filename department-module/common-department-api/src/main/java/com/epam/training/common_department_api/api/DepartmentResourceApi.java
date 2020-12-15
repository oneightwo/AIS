package com.epam.training.common_department_api.api;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.common_department_api.services.DepartmentResourceApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "department-server/departments", fallback = DepartmentResourceApiFallback.class)
public interface DepartmentResourceApi {

    @GetMapping("/{id}")
    DepartmentDto getDepartmentById(@PathVariable Long id);

    @GetMapping("/positions/{id}")
    PositionDto getPositionById(@PathVariable Long id);

    @GetMapping("/{id}/children/current")
    List<DepartmentDto> getCurrentChildren(@PathVariable Long id);

    @GetMapping("/{id}/parents")
    List<DepartmentDto> getParentsHierarchy(@PathVariable Long id);

    @GetMapping("/{id}/children")
    List<DepartmentDto> getChildrenHierarchy(@PathVariable Long id);

    @GetMapping("/{id}/info")
    DepartmentInformationDto getDepartmentInfoById(@PathVariable Long id);

    @GetMapping("/positions")
    List<PositionDto> getAllPositions();

    @PostMapping
    DepartmentDto createDepartment(@RequestBody @Valid DepartmentDto departmentDto);

    @PutMapping("/{id}/transfer")
    DepartmentDto transferDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentTransferDto departmentTransferDto);

    @PutMapping("/{id}")
    DepartmentDto renameDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentDto departmentDto);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Long id);

}
