package com.epam.training.common_department_api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class DepartmentDto extends DepartmentWithoutChildrenDto {

    private List<DepartmentDto> children;

}

