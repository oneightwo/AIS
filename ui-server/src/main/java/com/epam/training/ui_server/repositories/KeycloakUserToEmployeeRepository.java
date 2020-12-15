package com.epam.training.ui_server.repositories;

import com.epam.training.ui_server.models.KeycloakUserToEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeycloakUserToEmployeeRepository extends JpaRepository<KeycloakUserToEmployee, Long> {

    Optional<KeycloakUserToEmployee> findByKeycloakUserId(String keycloakUserId);

    Optional<KeycloakUserToEmployee> findByEmployee_Id(Long employeeId);
}
