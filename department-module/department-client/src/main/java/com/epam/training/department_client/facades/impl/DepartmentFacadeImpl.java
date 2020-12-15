package com.epam.training.department_client.facades.impl;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.department_client.facades.DepartmentFacade;
import com.epam.training.department_client.services.DepartmentProducer;
import com.epam.training.department_client.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentFacadeImpl implements DepartmentFacade {

    private final DepartmentService departmentService;
    private final DepartmentProducer departmentProducer;

    @Override
    public DepartmentDto rename(DepartmentDto departmentDto) {
        DepartmentDto updatedDepartment = departmentService.rename(departmentDto);
        departmentProducer.updated(updatedDepartment);
        return updatedDepartment;
    }

    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        DepartmentDto createdDepartment = departmentService.save(departmentDto);
        departmentProducer.created(createdDepartment);
        return createdDepartment;
    }

    @Override
    public DepartmentDto departmentTransfer(DepartmentTransferDto departmentTransferDto) {
        List<DepartmentDto> currentChildren = departmentService.getCurrentChildren(departmentTransferDto.getCurrentDepartmentId());
        DepartmentDto updatedDepartment = departmentService.departmentTransfer(departmentTransferDto);
        departmentProducer.updated(updatedDepartment);
        currentChildren.forEach(departmentDto -> {
            departmentProducer.updated(departmentService.getById(departmentDto.getId()));
        });
        return updatedDepartment;
    }

    @Override
    public void delete(Long departmentId) {
        DepartmentDto departmentDto = departmentService.getById(departmentId);
        departmentService.delete(departmentId);
        departmentProducer.deleted(departmentDto);
    }
}
