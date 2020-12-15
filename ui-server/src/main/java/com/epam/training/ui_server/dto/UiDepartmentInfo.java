package com.epam.training.ui_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UiDepartmentInfo {

    @JsonProperty("department_id")
    private Long id;

    private String name;

    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    private UiEmployee leader;

    @JsonProperty("number_employees")
    private Integer numberEmployees;
}
