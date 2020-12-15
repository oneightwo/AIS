package com.epam.training.ui_server.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "departments")
public class DepartmentSnapshot {

    @Id
    @Column(name = "department_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private DepartmentSnapshot parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<DepartmentSnapshot> children = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<EmployeeSnapshot> employeeSnapshots;
}
