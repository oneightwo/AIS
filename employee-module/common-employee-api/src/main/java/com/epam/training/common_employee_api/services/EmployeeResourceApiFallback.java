package com.epam.training.common_employee_api.services;

import com.epam.training.common_employee_api.api.EmployeeResourceApi;
import com.epam.training.common_employee_api.constants.EmployeeExceptionConstants;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.InternalErrorGettingResources;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class EmployeeResourceApiFallback implements EmployeeResourceApi {

    @Override
    public EmployeeDto getLeaderInDepartment(long id) {
        log.debug("INTERNAL_SERVER_ERROR getLeaderInDepartment");
        throw new InternalErrorGettingResources(EmployeeExceptionConstants.ERROR_GETTING_A_LEADER_IN_THE_DEPARTMENT);
    }

    @Override
    public List<EmployeeDto> getEmployeesByDepartmentId(long id) {
        log.debug("INTERNAL_SERVER_ERROR getEmployeesByDepartmentId");
        throw new InternalErrorGettingResources(EmployeeExceptionConstants.ERROR_GETTING_DEPARTMENT_EMPLOYEES);
    }

    @Override
    public List<EmployeeDto> getAll() {
        log.debug("INTERNAL_SERVER_ERROR getAll");
        return new ArrayList<>();
    }

    @Override
    public EmployeeDto updateEmployee(Long id, @Valid EmployeeDto employeeDto) {
        log.debug("INTERNAL_SERVER_ERROR updateEmployee");
        throw new InternalErrorGettingResources("INTERNAL_SERVER_ERROR updateEmployee");
    }

    @Override
    public EmployeeDto dismissEmployee(Long id, @Valid DismissalEmployeeDto dismissalEmployeeDto) {
        log.debug("INTERNAL_SERVER_ERROR dismissEmployee");
        throw new InternalErrorGettingResources("INTERNAL_SERVER_ERROR dismissEmployee");
    }

    @Override
    public EmployeeDto saveEmployee(@Valid EmployeeDto employeeDto) {
        log.debug("INTERNAL_SERVER_ERROR saveEmployee");
        throw new InternalErrorGettingResources("INTERNAL_SERVER_ERROR saveEmployee");
    }
}
