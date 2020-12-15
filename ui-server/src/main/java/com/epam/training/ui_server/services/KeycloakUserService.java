package com.epam.training.ui_server.services;

import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.ui_server.models.EmployeeSnapshot;

import java.security.Principal;
import java.util.Set;

public interface KeycloakUserService {

    void createUser(EmployeeSnapshot employee);

    void deleteUser(DismissalEmployeeDto employee);

    void updateUser(EmployeeSnapshot employee);

    Set<String> getRole(Principal principal);
}
