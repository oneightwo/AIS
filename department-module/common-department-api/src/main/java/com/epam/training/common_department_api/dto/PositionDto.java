package com.epam.training.common_department_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PositionDto {

    @JsonProperty("position_id")
    private Long id;

    private String name;
}
