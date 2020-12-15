package com.epam.training.employee_client.config.mappers;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.employee_client.models.DepartmentSnapshot;
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
        mapper.classMap(DepartmentSnapshot.class, DepartmentDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(DepartmentSnapshot departmentSnapshot, DepartmentDto departmentDto, MappingContext context) {
                        DepartmentSnapshot parent = departmentSnapshot.getParent();
                        Long parentId = Objects.isNull(parent) ? null : parent.getId();
                        departmentDto.setParentId(parentId);
                        List<DepartmentDto> children;
                        if (Objects.nonNull(departmentSnapshot.getChildren())) {
                            children = departmentSnapshot.getChildren().stream()
                                    .map(entity -> mapperFacade.map(entity, DepartmentDto.class))
                                    .collect(Collectors.toList());
                        } else {
                            children = Collections.emptyList();
                        }
                        departmentDto.setChildren(children);
                        super.mapAtoB(departmentSnapshot, departmentDto, context);
                    }

                    @Override
                    public void mapBtoA(DepartmentDto departmentDto, DepartmentSnapshot departmentSnapshot, MappingContext context) {
                        if (Objects.nonNull(departmentDto.getParentId())) {
                            var parent = new DepartmentSnapshot();
                            parent.setId(departmentDto.getParentId());
                            departmentSnapshot.setParent(parent);
                        }
                        super.mapBtoA(departmentDto, departmentSnapshot, context);
                    }
                })
                .byDefault()
                .register();
    }
}
