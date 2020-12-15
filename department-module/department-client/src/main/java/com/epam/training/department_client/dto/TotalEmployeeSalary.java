package com.epam.training.department_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TotalEmployeeSalary {

    @JsonProperty("department_id")
    private Long departmentId;

    @JsonProperty("total_salary")
    private Double totalSalary;
}
