package com.epam.training.department_client.config.mappers;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.department_client.models.Department;
import com.epam.training.department_client.models.EmployeeSnapshot;
import com.epam.training.department_client.models.Position;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapper) {
        mapper.classMap(EmployeeSnapshot.class, EmployeeDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(EmployeeSnapshot employeeSnapshot, EmployeeDto employeeDto, MappingContext context) {
                        employeeDto.setDepartmentId(employeeSnapshot.getDepartment().getId());
                        employeeDto.setPositionId(employeeSnapshot.getPosition().getId());
                        super.mapAtoB(employeeSnapshot, employeeDto, context);
                    }

                    @Override
                    public void mapBtoA(EmployeeDto employeeDto, EmployeeSnapshot employeeSnapshot, MappingContext context) {
                        var department = new Department();
                        var position = new Position();
                        department.setId(employeeDto.getDepartmentId());
                        position.setId(employeeDto.getPositionId());
                        employeeSnapshot.setDepartment(department);
                        employeeSnapshot.setPosition(position);
                        super.mapBtoA(employeeDto, employeeSnapshot, context);
                    }
                })
                .byDefault()
                .register();
    }
}
