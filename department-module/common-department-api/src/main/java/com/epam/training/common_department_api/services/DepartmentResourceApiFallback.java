package com.epam.training.common_department_api.services;

import com.epam.training.common_department_api.api.DepartmentResourceApi;
import com.epam.training.common_department_api.constants.DepartmentExceptionConstants;
import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.core_common_api.exceptions.InternalErrorGettingResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
@Slf4j
public class DepartmentResourceApiFallback implements DepartmentResourceApi {

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        log.debug("INTERNAL_SERVER_ERROR getDepartmentById");
        throw new InternalErrorGettingResources(DepartmentExceptionConstants.ERROR_WHEN_GETTING_A_DEPARTMENT);
    }

    @Override
    public PositionDto getPositionById(Long id) {
        log.debug("INTERNAL_SERVER_ERROR getPositionById");
        throw new InternalErrorGettingResources(DepartmentExceptionConstants.ERROR_WHEN_GETTING_A_POSITION);
    }

    @Override
    public List<DepartmentDto> getCurrentChildren(Long id) {
        throw new InternalErrorGettingResources("ERROR getCurrentChildren");
    }

    @Override
    public List<DepartmentDto> getParentsHierarchy(Long id) {
        throw new InternalErrorGettingResources("ERROR getParentsHierarchy");
    }

    @Override
    public List<DepartmentDto> getChildrenHierarchy(Long id) {
        throw new InternalErrorGettingResources("ERROR getChildrenHierarchy");
    }

    @Override
    public DepartmentInformationDto getDepartmentInfoById(Long id) {
        throw new InternalErrorGettingResources("ERROR getDepartmentInfoById");
    }

    @Override
    public List<PositionDto> getAllPositions() {
        throw new InternalErrorGettingResources("ERROR getAllPositions");
    }

    @Override
    public DepartmentDto createDepartment(@Valid DepartmentDto departmentDto) {
        throw new InternalErrorGettingResources("ERROR createDepartment");
    }

    @Override
    public DepartmentDto transferDepartment(Long id, @Valid DepartmentTransferDto departmentTransferDto) {
        throw new InternalErrorGettingResources("ERROR transferDepartment");
    }

    @Override
    public DepartmentDto renameDepartment(Long id, @Valid DepartmentDto departmentDto) {
        throw new InternalErrorGettingResources("ERROR renameDepartment");
    }

    @Override
    public void deleteById(Long id) {
        throw new InternalErrorGettingResources("ERROR deleteById");
    }
}
