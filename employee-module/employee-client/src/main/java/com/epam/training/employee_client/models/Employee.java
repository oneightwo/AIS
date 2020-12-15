package com.epam.training.employee_client.models;

import com.epam.training.common_employee_api.models.Gender;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;

    private String patronymic;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(name = "employment_date", nullable = false)
    private LocalDate employmentDate;

    @Column(name = "date_of_dismissal")
    private LocalDate dateOfDismissal;

    @Column(name = "position_id", nullable = false)
    private Long positionId;

    @Column(nullable = false)
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private DepartmentSnapshot department;

    @Column(name = "is_leader", nullable = false)
    private Boolean isLeader;
}
