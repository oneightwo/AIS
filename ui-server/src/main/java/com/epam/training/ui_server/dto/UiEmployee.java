package com.epam.training.ui_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UiEmployee {

    @JsonProperty("employee_id")
    private Long id;

    private String surname;

    private String name;

    private String patronymic;

    private String gender;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    private String phone;

    private String email;

    @JsonProperty("employment_date")
    private LocalDate employmentDate;

    @JsonProperty("date_of_dismissal")
    private LocalDate dateOfDismissal;

    @JsonProperty("position_name")
    private String positionName;

    private Double salary;

    @JsonProperty("department_name")
    private String departmentName;

    @JsonProperty("is_leader")
    private String isLeader;
}
