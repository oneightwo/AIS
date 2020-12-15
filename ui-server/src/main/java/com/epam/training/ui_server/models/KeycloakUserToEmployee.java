package com.epam.training.ui_server.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "keycloak_users_to_employees")
public class KeycloakUserToEmployee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "keycloak_user_id")
    private String keycloakUserId;

    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeeSnapshot employee;
}
