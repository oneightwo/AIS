package com.epam.training.department_client.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", allocationSize = 1)
    @Column(name = "report_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "salary_fund")
    private Double salaryFund;
}
