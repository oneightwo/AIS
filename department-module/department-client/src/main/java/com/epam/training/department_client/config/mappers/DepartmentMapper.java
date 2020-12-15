package com.epam.training.department_client.config.mappers;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentWithoutChildrenDto;
import com.epam.training.department_client.models.Department;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class DepartmentMapper implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapper) {
        mapper.classMap(Department.class, DepartmentDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Department department, DepartmentDto departmentDto, MappingContext context) {
                        Department parent = department.getParent();
                        Long parentId = Objects.isNull(parent) ? null : parent.getId();
                        departmentDto.setParentId(parentId);
                        List<DepartmentDto> children;
                        if (Objects.nonNull(department.getChildren())) {
                            children = department.getChildren().stream()
                                    .map(entity -> mapperFacade.map(entity, DepartmentDto.class))
                                    .collect(Collectors.toList());
                        } else {
                            children = Collections.emptyList();
                        }
                        departmentDto.setChildren(children);
                        super.mapAtoB(department, departmentDto, context);
                    }

                    @Override
                    public void mapBtoA(DepartmentDto departmentDto, Department department, MappingContext context) {
                        if (Objects.nonNull(departmentDto.getParentId())) {
                            var parent = new Department();
                            parent.setId(departmentDto.getParentId());
                            department.setParent(parent);
                        }
                        super.mapBtoA(departmentDto, department, context);
                    }
                })
                .byDefault()
                .register();

        mapper.classMap(Department.class, DepartmentWithoutChildrenDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Department department, DepartmentWithoutChildrenDto departmentWithoutChildrenDto, MappingContext context) {
                        Department parent = department.getParent();
                        Long parentId = Objects.isNull(parent) ? null : department.getParent().getId();
                        departmentWithoutChildrenDto.setParentId(parentId);
                        super.mapAtoB(department, departmentWithoutChildrenDto, context);
                    }

                    @Override
                    public void mapBtoA(DepartmentWithoutChildrenDto departmentWithoutChildrenDto, Department department, MappingContext context) {
                        if (Objects.nonNull(departmentWithoutChildrenDto.getParentId())) {
                            var parent = new Department();
                            parent.setId(departmentWithoutChildrenDto.getParentId());
                            department.setParent(parent);
                        }
                        super.mapBtoA(departmentWithoutChildrenDto, department, context);
                    }
                })
                .byDefault()
                .register();
    }
}
