package com.epam.training.department_client.data;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.department_client.models.Department;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DepartmentTestDataProvider {

    public static final String DEPARTMENT_NAME = "TEST";
    public static final Long FIRST_DEPARTMENT_ID = 1L;
    public static final Long SECOND_DEPARTMENT_ID = 2L;
    public static final Long THIRD_DEPARTMENT_ID = 3L;
    public static final Long INCORRECT_DEPARTMENT_ID = 182L;
    public static final LocalDateTime CREATION_DATE = LocalDateTime.of(1975, 2, 18, 23, 59);

    public static Department getDepartmentWithChildren(Long departmentId) {
        var department = new Department();
        department.setId(departmentId);
        department.setName(DEPARTMENT_NAME);
        department.setCreationDate(CREATION_DATE);
        department.setChildren(List.of(getDepartmentWithChildren(SECOND_DEPARTMENT_ID, FIRST_DEPARTMENT_ID,
                List.of(getDepartmentWithParentWithoutChildren(THIRD_DEPARTMENT_ID, SECOND_DEPARTMENT_ID)))));
        department.setParent(null);
        return department;
    }

    public static Department getDepartmentWithParentWithoutChildren(Long departmentId, Long parentId) {
        var department = new Department();
        department.setId(departmentId);
        department.setName(DEPARTMENT_NAME);
        department.setCreationDate(CREATION_DATE);
        department.setChildren(new ArrayList<>(Collections.emptyList()));
        department.setParent(getDepartmentWithoutChildrenAndParent(parentId));
        return department;
    }

    public static Department getDepartmentWithChildren(Long departmentId, Long parentId, List<Department> children) {
        var department = new Department();
        department.setId(departmentId);
        department.setName(DEPARTMENT_NAME);
        department.setCreationDate(CREATION_DATE);
        department.setChildren(children);
        department.setParent(getDepartmentWithoutChildrenAndParent(parentId));
        return department;
    }

    public static Department getDepartmentWithoutChildrenAndParent(Long departmentId) {
        var department = new Department();
        department.setId(departmentId);
        department.setName(DEPARTMENT_NAME);
        department.setCreationDate(CREATION_DATE);
        department.setChildren(new ArrayList<>(Collections.emptyList()));
        department.setParent(null);
        return department;
    }

    public static DepartmentDto map(Department department) {
        var departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setName(department.getName());
        departmentDto.setCreationDate(department.getCreationDate());
        departmentDto.setParentId(Objects.isNull(department.getParent()) ? null : department.getParent().getId());
        departmentDto.setChildren(Objects.nonNull(department.getChildren()) ? department.getChildren().stream()
                .map(DepartmentTestDataProvider::map)
                .collect(Collectors.toList()) : Collections.emptyList());
        return departmentDto;
    }

    public static List<Department> map(List<DepartmentDto> departmentDtoList) {
        List<Department> departmentList = new ArrayList<>();
        departmentDtoList.forEach(departmentDto -> departmentList.add(map(departmentDto)));
        return departmentList;
    }

    public static Department map(DepartmentDto departmentDto) {
        var department = new Department();
        department.setId(departmentDto.getId());
        department.setName(departmentDto.getName());
        department.setCreationDate(departmentDto.getCreationDate());
        department.setChildren(Objects.nonNull(departmentDto.getChildren()) ? departmentDto.getChildren().stream()
                .map(DepartmentTestDataProvider::map)
                .collect(Collectors.toList()) : Collections.emptyList());
        if (Objects.nonNull(departmentDto.getParentId())) {
            department.setParent(getDepartmentWithoutChildrenAndParent(departmentDto.getParentId()));
        }
        return department;
    }

}
