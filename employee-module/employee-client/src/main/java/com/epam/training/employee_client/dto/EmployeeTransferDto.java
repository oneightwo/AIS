package com.epam.training.employee_client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EmployeeTransferDto {

    @JsonProperty("employee_id")
    private Long employeeId;

    @NotNull
    @JsonProperty("destination_department_id")
    private Long destinationDepartmentId;

}
