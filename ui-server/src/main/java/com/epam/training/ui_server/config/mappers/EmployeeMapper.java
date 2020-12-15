package com.epam.training.ui_server.config.mappers;

import com.epam.training.common_department_api.api.DepartmentResourceApi;
import com.epam.training.common_department_api.constants.DepartmentExceptionConstants;
import com.epam.training.common_department_api.constants.PositionExceptionConstants;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.models.Gender;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.ui_server.dto.UiEmployee;
import com.epam.training.ui_server.models.DepartmentSnapshot;
import com.epam.training.ui_server.models.EmployeeSnapshot;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper implements OrikaMapperFactoryConfigurer {

    private final DepartmentResourceApi departmentResourceApi;

    @Override
    public void configure(MapperFactory mapper) {
        mapper.classMap(EmployeeSnapshot.class, EmployeeDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(EmployeeSnapshot employeeSnapshot, EmployeeDto employeeDto, MappingContext context) {
                        super.mapAtoB(employeeSnapshot, employeeDto, context);
                        employeeDto.setDepartmentId(employeeSnapshot.getDepartment().getId());
                        employeeDto.setPositionId(employeeSnapshot.getPositionId());
                    }

                    @Override
                    public void mapBtoA(EmployeeDto employeeDto, EmployeeSnapshot employeeSnapshot, MappingContext context) {
                        super.mapBtoA(employeeDto, employeeSnapshot, context);
                        var department = new DepartmentSnapshot();
                        department.setId(employeeDto.getDepartmentId());
                        employeeSnapshot.setDepartment(department);
                        employeeSnapshot.setPositionId(employeeDto.getPositionId());
                    }
                })
                .byDefault()
                .register();

        mapper.classMap(EmployeeSnapshot.class, UiEmployee.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(EmployeeSnapshot employeeSnapshot, UiEmployee uiEmployee, MappingContext context) {
                        super.mapAtoB(employeeSnapshot, uiEmployee, context);
                        uiEmployee.setGender(employeeSnapshot.getGender().equals(Gender.MALE) ? "Мужчина" : "Женщина");
                        uiEmployee.setDepartmentName(employeeSnapshot.getDepartment().getName());
                        try {
                            uiEmployee.setPositionName(departmentResourceApi.getPositionById(employeeSnapshot.getPositionId()).getName());
                        } catch (FeignException e) {
                            throw new ObjectNotFoundException(PositionExceptionConstants.POSITION_WITH_THIS_ID_DOES_NOT_EXIST);
                        }
                        uiEmployee.setIsLeader(employeeSnapshot.getIsLeader() ? "Да" : "Нет");
                    }

                    @Override
                    public void mapBtoA(UiEmployee uiEmployee, EmployeeSnapshot employeeSnapshot, MappingContext context) {
                        super.mapBtoA(uiEmployee, employeeSnapshot, context);
                    }
                })
                .byDefault()
                .register();

        mapper.classMap(EmployeeDto.class, UiEmployee.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(EmployeeDto employeeDto, UiEmployee uiEmployee, MappingContext context) {
                        super.mapAtoB(employeeDto, uiEmployee, context);
                        uiEmployee.setGender(employeeDto.getGender().equals(Gender.MALE) ? "Мужчина" : "Женщина");
                        try {
                            uiEmployee.setDepartmentName(departmentResourceApi.getDepartmentById(employeeDto.getDepartmentId()).getName());
                        } catch (FeignException e) {
                            throw new ObjectNotFoundException(DepartmentExceptionConstants.ERROR_WHEN_GETTING_A_DEPARTMENT);
                        }
                        try {
                            uiEmployee.setPositionName(departmentResourceApi.getPositionById(employeeDto.getPositionId()).getName());
                        } catch (FeignException e) {
                            throw new ObjectNotFoundException(DepartmentExceptionConstants.ERROR_WHEN_GETTING_A_POSITION);
                        }
                        uiEmployee.setIsLeader(employeeDto.getIsLeader() ? "Да" : "Нет");
                    }

                    @Override
                    public void mapBtoA(UiEmployee uiEmployee, EmployeeDto employeeDto, MappingContext context) {
                        super.mapBtoA(uiEmployee, employeeDto, context);
                    }
                })
                .byDefault()
                .register();
    }
}
