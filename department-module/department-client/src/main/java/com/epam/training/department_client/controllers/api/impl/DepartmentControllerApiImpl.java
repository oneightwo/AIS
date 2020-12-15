package com.epam.training.department_client.controllers.api.impl;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.department_client.controllers.api.DepartmentControllerApi;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.department_client.dto.TotalEmployeeSalary;
import com.epam.training.department_client.facades.DepartmentFacade;
import com.epam.training.department_client.services.DepartmentService;
import com.epam.training.department_client.services.PositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Slf4j
public class DepartmentControllerApiImpl implements DepartmentControllerApi {

    private final DepartmentService departmentService;
    private final DepartmentFacade departmentFacade;
    private final PositionService positionService;

    @Override
    public DepartmentDto createDepartment(@Valid DepartmentDto departmentDto) {
        return departmentFacade.save(departmentDto);
    }

    @Override
    public DepartmentDto renameDepartment(Long id, @Valid DepartmentDto departmentDto) {
        departmentDto.setId(id);
        return departmentFacade.rename(departmentDto);
    }

    @Override
    public DepartmentInformationDto getDepartmentInfoById(Long id) {
        return departmentService.getInformationById(id);
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        return departmentService.getById(id);
    }

    @Override
    public DepartmentDto getDepartmentByName(String name) {
        return departmentService.getByName(name);
    }

    @Override
    public DepartmentDto transferDepartment(Long id, @Valid DepartmentTransferDto departmentTransferDto) {
        departmentTransferDto.setCurrentDepartmentId(id);
        return departmentFacade.departmentTransfer(departmentTransferDto);
    }

    @Override
    public List<DepartmentDto> getCurrentChildren(Long id) {
        return departmentService.getCurrentChildren(id);
    }

    @Override
    public List<DepartmentDto> getParentsHierarchy(Long id) {
        return departmentService.getParentsHierarchy(id);
    }

    @Override
    public List<DepartmentDto> getChildrenHierarchy(Long id) {
        return departmentService.getChildrenHierarchy(id);
    }

    @Override
    public List<PositionDto> getAllPositions() {
        return positionService.getAll();
    }

    @Override
    public PositionDto getPositionById(Long id) {
        return positionService.getById(id);
    }

    @Override
    public TotalEmployeeSalary getEmployeeSalaryByDepartmentId(Long id) {
        return departmentService.getEmployeeSalaryByDepartmentId(id);
    }

    @Override
    public void deleteById(Long id) {
        departmentFacade.delete(id);
    }

}
