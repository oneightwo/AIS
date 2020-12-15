package com.epam.training.common_department_api.dto;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DepartmentInformationDto {

    @JsonProperty("department_id")
    private Long id;

    private String name;

    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    private EmployeeDto leader;

    @JsonProperty("number_employees")
    private Integer numberEmployees;
}
