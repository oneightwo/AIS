package com.epam.training.common_department_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DepartmentTransferDto {

    @JsonProperty("current_department_id")
    private Long currentDepartmentId;

    @NotNull
    @JsonProperty("destination_department_id")
    private Long destinationDepartmentId;
}
