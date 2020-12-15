package com.epam.training.employee_client.config.mappers;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.models.DepartmentSnapshot;
import com.epam.training.employee_client.models.Employee;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EmployeeMapper implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapper) {
        mapper.classMap(Employee.class, EmployeeDto.class)
                .customize(new CustomMapper<Employee, EmployeeDto>() {
                    @Override
                    public void mapAtoB(Employee employee, EmployeeDto employeeDto, MappingContext context) {
                        if (Objects.nonNull(employee.getDepartment())) {
                            employeeDto.setDepartmentId(employee.getDepartment().getId());
                        } else {
                            employeeDto.setDepartmentId(null);
                        }
                        super.mapAtoB(employee, employeeDto, context);
                    }

                    @Override
                    public void mapBtoA(EmployeeDto employeeDto, Employee employee, MappingContext context) {
                        var department = new DepartmentSnapshot();
                        department.setId(employeeDto.getDepartmentId());
                        employee.setDepartment(department);
                        super.mapBtoA(employeeDto, employee, context);
                    }
                })
                .byDefault()
                .register();

        mapper.classMap(DismissalEmployeeDto.class, EmployeeDto.class)
                .byDefault()
                .register();
    }
}
