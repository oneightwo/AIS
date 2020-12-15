package com.epam.training.common_employee_api.dto;

import com.epam.training.common_employee_api.constants.CommonEmployeeRegexp;
import com.epam.training.common_employee_api.models.Gender;
import com.epam.training.common_employee_api.validators.annotations.ComparingDateFields;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@ComparingDateFields.List({
        @ComparingDateFields(dateA = "dateOfBirth", dateB = "employmentDate", comparing = ComparingDateFields.Comparing.B_MORE_A),
        @ComparingDateFields(dateA = "employmentDate", dateB = "dateOfDismissal", comparing = ComparingDateFields.Comparing.B_MORE_A)
})
@Data
@NoArgsConstructor
public class EmployeeDto {

    @JsonProperty("employee_id")
    private Long id;

    @NotNull
    @Pattern(regexp = CommonEmployeeRegexp.CHECKING_RUSSIAN_LETTERS)
    private String surname;

    @NotNull
    @Pattern(regexp = CommonEmployeeRegexp.CHECKING_RUSSIAN_LETTERS)
    private String name;

    @Pattern(regexp = CommonEmployeeRegexp.CHECKING_RUSSIAN_LETTERS)
    private String patronymic;

    @NotNull
    private Gender gender;

    @NotNull
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull
    @Pattern(regexp = CommonEmployeeRegexp.CHECKING_PHONE_NUMBER)
    private String phone;

    @NotNull
    @Email
    private String email;

    @NotNull
    @JsonProperty("employment_date")
    private LocalDate employmentDate;

    @JsonProperty("date_of_dismissal")
    private LocalDate dateOfDismissal;

    @JsonProperty("position_id")
    private Long positionId;

    @Min(1)
    @Digits(integer = Integer.MAX_VALUE, fraction = 2)
    private Double salary;

    @NotNull
    @JsonProperty("department_id")
    private Long departmentId;

    @NotNull
    @JsonProperty("is_leader")
    private Boolean isLeader;

}
