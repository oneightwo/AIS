package com.epam.training.common_department_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DepartmentWithoutChildrenDto {

    @JsonProperty("department_id")
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @JsonProperty("creation_date")
    private LocalDateTime creationDate;

    @JsonProperty("parent_id")
    private Long parentId;
}
