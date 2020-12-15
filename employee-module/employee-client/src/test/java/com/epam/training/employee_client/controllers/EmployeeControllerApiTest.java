package com.epam.training.employee_client.controllers;

import com.epam.training.common_department_api.api.DepartmentResourceApi;
import com.epam.training.employee_client.data.PositionTestDataProvider;
import com.epam.training.employee_client.services.EmployeeProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.yml")
@ActiveProfiles("test")
@Sql(value = "/db/sql/create-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/db/sql/clear-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EmployeeControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeProducer employeeProducer;
    @MockBean
    private DepartmentResourceApi departmentResourceApi;

    @BeforeEach
    void init() {
        when(departmentResourceApi.getPositionById(PositionTestDataProvider.FIRST_POSITION_ID))
                .thenReturn(PositionTestDataProvider.getPositionDto(PositionTestDataProvider.FIRST_POSITION_ID));
    }

    @Test
    void saveEmployee() throws Exception {
        String jsonBody = "{\n" +
                "    \"surname\": \"Тест\",\n" +
                "    \"name\": \"Тест\",\n" +
                "    \"patronymic\": \"Тест\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"phone\": \"88005553535\",\n" +
                "    \"email\": \"oneightwo@gmail.com\",\n" +
                "    \"salary\": 30100,\n" +
                "    \"date_of_birth\": \"2000-09-04\",\n" +
                "    \"employment_date\": \"2010-03-24\",\n" +
                "    \"position_id\": 1,\n" +
                "    \"department_id\": 1,\n" +
                "    \"is_leader\": false\n" +
                "}";
        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("employee_id").value(4))
                .andExpect(jsonPath("surname").value("Тест"))
                .andExpect(jsonPath("name").value("Тест"))
                .andExpect(jsonPath("patronymic").value("Тест"));
    }

    @Test
    void getEmployeeById() throws Exception {
        mockMvc.perform(get("/employees/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("employee_id").value(1));
    }

    @Test
    void getEmployeesByDepartmentId() throws Exception {
        mockMvc.perform(get("/employees/departments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[*].employee_id", containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].employee_id", containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].employee_id", containsInAnyOrder(1, 2, 3)));
    }

    @Test
    void updateEmployee() throws Exception {
        String jsonBody = "{\n" +
                "    \"surname\": \"ТестДва\",\n" +
                "    \"name\": \"ТестДва\",\n" +
                "    \"patronymic\": \"ТестДва\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"phone\": \"88005553535\",\n" +
                "    \"email\": \"oneightwo@gmail.com\",\n" +
                "    \"salary\": 30100,\n" +
                "    \"date_of_birth\": \"2000-09-04\",\n" +
                "    \"employment_date\": \"2010-03-24\",\n" +
                "    \"position_id\": 1,\n" +
                "    \"department_id\": 1,\n" +
                "    \"is_leader\": false\n" +
                "}";
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("employee_id").value(1))
                .andExpect(jsonPath("surname").value("ТестДва"))
                .andExpect(jsonPath("name").value("ТестДва"))
                .andExpect(jsonPath("patronymic").value("ТестДва"));
    }

    @Test
    void getLeaderInDepartment() throws Exception {
        mockMvc.perform(get("/employees/departments/1/leaders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("employee_id").value(1))
                .andExpect(jsonPath("is_leader").value(true));
    }

    @Test
    void transferEmployeeToAnotherDepartment() throws Exception {
        String jsonBody = "{\n" +
                "    \"destination_department_id\": 2\n" +
                "}";
        mockMvc.perform(put("/employees/2/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("employee_id").value(2))
                .andExpect(jsonPath("department_id").value(2));
    }

    @Test
    void dismissEmployee() throws Exception {
        String jsonBody = "{\n" +
                "    \"surname\": \"Тест\",\n" +
                "    \"name\": \"Тест\",\n" +
                "    \"patronymic\": \"Тест\",\n" +
                "    \"gender\": \"MALE\",\n" +
                "    \"phone\": \"88005553535\",\n" +
                "    \"email\": \"oneightwo@gmail.com\",\n" +
                "    \"salary\": 30100,\n" +
                "    \"date_of_birth\": \"2000-09-04\",\n" +
                "    \"employment_date\": \"2010-03-24\",\n" +
                "    \"position_id\": 1,\n" +
                "    \"department_id\": 1,\n" +
                "    \"is_leader\": false,\n" +
                "    \"date_of_dismissal\": \"2020-06-03\"\n" +
                "}";
        mockMvc.perform(delete("/employees/2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("employee_id").value(2))
                .andExpect(jsonPath("date_of_dismissal").value("2020-06-03"));
    }

    @Test
    void getEmployeeLeader() throws Exception {
        mockMvc.perform(get("/employees/2/leaders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("employee_id").value(1))
                .andExpect(jsonPath("is_leader").value(true));
    }

    @Test
    void transferEmployeesToAnotherDepartment() throws Exception {
        String jsonBody = "{\n" +
                "    \"current_department_id\": 1,\n" +
                "    \"destination_department_id\": 2\n" +
                "}";
        mockMvc.perform(put("/employees/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andDo(print())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[*].employee_id", containsInAnyOrder(1, 2, 3)))
                .andExpect(jsonPath("$[*].department_id", contains(2, 2, 2)));
    }

    @Test
    void getEmployeesByField() throws Exception {
        mockMvc.perform(get("/employees?positionId=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[*].employee_id", contains(1)))
                .andExpect(jsonPath("$[*].position_id", contains(1)));
    }


}
