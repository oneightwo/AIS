package com.epam.training.ui_server.services.impl;

import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.ui_server.models.EmployeeSnapshot;
import com.epam.training.ui_server.models.KeycloakUserToEmployee;
import com.epam.training.ui_server.repositories.KeycloakUserToEmployeeRepository;
import com.epam.training.ui_server.services.KeycloakUserService;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class KeycloakUserServiceImpl implements KeycloakUserService {

    private final KeycloakUserToEmployeeRepository keycloakUserToEmployeeRepository;

    @Value("${keycloak.auth-server-url}")
    public String serverUrl;

    @Value("${keycloak.realm}")
    public String realm;

    @Value("${keycloak.resource}")
    public String clientId;

    @Value("${keycloak.credentials.secret}")
    public String clientSecret;

    @Override
    public void createUser(EmployeeSnapshot employee) {
        Keycloak keycloak = getKeycloakInstance();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        String username = employee.getName().toLowerCase() + "_" + employee.getSurname().toLowerCase();
        user.setUsername(username);
        user.setFirstName(employee.getName());
        user.setLastName(employee.getSurname());
        user.setEmail(employee.getEmail());

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        Response response = usersResource.create(user);
        String userId = CreatedResponseUtil.getCreatedId(response);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(username);

        UserResource userResource = usersResource.get(userId);
        userResource.resetPassword(passwordCred);

        RoleRepresentation appUser = realmResource.roles()
                .get(employee.getIsLeader() ? "app-admin" : "app-user").toRepresentation();
        userResource.roles().realmLevel()
                .add(List.of(appUser));

        var keycloakUserToEmployee = new KeycloakUserToEmployee();
        keycloakUserToEmployee.setKeycloakUserId(userId);
        keycloakUserToEmployee.setEmployee(employee);
        keycloakUserToEmployeeRepository.save(keycloakUserToEmployee);
    }

    @Override
    public void deleteUser(DismissalEmployeeDto employee) {
        Keycloak keycloak = getKeycloakInstance();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        KeycloakUserToEmployee keycloakUserToEmployee = keycloakUserToEmployeeRepository.findByEmployee_Id(employee.getId())
                .orElseThrow(() -> new RuntimeException("ERROR"));
        UserResource userResource = usersResource.get(keycloakUserToEmployee.getKeycloakUserId());
        userResource.remove();
    }

    @Override
    public void updateUser(EmployeeSnapshot employee) {
        Keycloak keycloak = getKeycloakInstance();

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        KeycloakUserToEmployee keycloakUserToEmployee = keycloakUserToEmployeeRepository.findByEmployee_Id(employee.getId())
                .orElseThrow(() -> new RuntimeException("ERROR"));
        UserResource userResource = usersResource.get(keycloakUserToEmployee.getKeycloakUserId());
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
//        String username = employee.getName().toLowerCase() + "_" + employee.getSurname().toLowerCase();
//        user.setUsername(username);
        user.setFirstName(employee.getName());
        user.setLastName(employee.getSurname());
        user.setEmail(employee.getEmail());
        userResource.update(user);
    }

    @Override
    public Set<String> getRole(Principal principal) {
        return ((SimpleKeycloakAccount) ((KeycloakAuthenticationToken) principal).getDetails()).getRoles();
    }

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                serverUrl,
                "master",
                "oneightwo",
                "1234",
                "admin-cli");
    }


    private UsersResource getUserResource(EmployeeSnapshot employee) {
        Keycloak keycloak = getKeycloakInstance();

        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        String username = employee.getName().toLowerCase() + "_" + employee.getSurname().toLowerCase();
        user.setUsername(username);
        user.setFirstName(employee.getName());
        user.setLastName(employee.getSurname());
        user.setEmail(employee.getEmail());

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        return usersResource;
    }
}
