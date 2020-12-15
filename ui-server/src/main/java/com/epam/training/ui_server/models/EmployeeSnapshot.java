package com.epam.training.ui_server.models;

import com.epam.training.common_employee_api.models.Gender;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class EmployeeSnapshot {

    @Id
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    private String patronymic;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "employment_date", nullable = false)
    private LocalDate employmentDate;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentSnapshot department;

    @Column(name = "date_of_dismissal")
    private LocalDate dateOfDismissal;

    @Column(name = "position_id", nullable = false)
    private Long positionId;

    private Double salary;

    @Column(name = "is_leader", nullable = false)
    private Boolean isLeader;

}
