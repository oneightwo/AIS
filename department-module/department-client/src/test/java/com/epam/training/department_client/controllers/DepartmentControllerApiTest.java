package com.epam.training.department_client.controllers;

import com.epam.training.department_client.services.DepartmentProducer;
import com.google.gson.Gson;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
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
public class DepartmentControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentProducer departmentProducer;

    private final Gson gson = new Gson();

    @Test
    void createDepartment() throws Exception {
        String jsonBody = "{\n" +
                "    \"name\": \"dep5\",\n" +
                "    \"parent_id\": 3\n" +
                "}";
        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("department_id").value(5))
                .andExpect(jsonPath("name").value("dep5"))
                .andExpect(jsonPath("parent_id").value(3));
    }

    @Test
    void renameDepartment() throws Exception {
        String jsonBody = "{\n" +
                "    \"name\": \"dep1_rename\"\n" +
                "}";
        mockMvc.perform(put("/departments/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("department_id").value(1))
                .andExpect(jsonPath("name").value("dep1_rename"))
                .andExpect(jsonPath("parent_id").isEmpty());
    }

    @Test
    void getDepartmentInfoById() throws Exception {
        mockMvc.perform(get("/departments/1/info"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("dep1"))
                .andExpect(jsonPath("leader").isNotEmpty())
                .andExpect(jsonPath("creation_date").isNotEmpty())
                .andExpect(jsonPath("number_employees").value(3));
    }

    @Test
    void getDepartmentById() throws Exception {
        mockMvc.perform(get("/departments/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("department_id").value(1))
                .andExpect(jsonPath("name").value("dep1"))
                .andExpect(jsonPath("parent_id").isEmpty());
    }

    @Test
    void getDepartmentByName() throws Exception {
        mockMvc.perform(get("/departments?name=dep1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("department_id").value(1))
                .andExpect(jsonPath("name").value("dep1"))
                .andExpect(jsonPath("parent_id").isEmpty());
    }

    @Test
    void transferDepartment() throws Exception {
        String jsonBody = "{\n" +
                "    \"destination_department_id\": 3\n" +
                "}";
        mockMvc.perform(put("/departments/1/transfer")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("department_id").value(1))
                .andExpect(jsonPath("name").value("dep1"))
                .andExpect(jsonPath("parent_id").value(3));
    }

    @Test
    void getCurrentChildren() throws Exception {
        mockMvc.perform(get("/departments/1/children/current"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(2, 4)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(2, 4)));
    }

    @Test
    void getParentsHierarchy() throws Exception {
        mockMvc.perform(get("/departments/3/parents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(1, 2)));
    }

    @Test
    void getChildrenHierarchy() throws Exception {
        mockMvc.perform(get("/departments/1/children"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(2, 3, 4)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(2, 3, 4)))
                .andExpect(jsonPath("$[*].department_id", containsInAnyOrder(2, 3, 4)));
    }

    @Test
    void getAllPositions() throws Exception {
        mockMvc.perform(get("/departments/positions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    void getPositionById() throws Exception {
        mockMvc.perform(get("/departments/positions/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("position_id").value(1L));
    }

    @Test
    void getEmployeeSalaryByDepartmentId() throws Exception {
        mockMvc.perform(get("/departments/1/salaries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("department_id").value(1))
                .andExpect(jsonPath("total_salary").value(445000));
    }

    @Test
    void deleteById() throws Exception {

        mockMvc.perform(delete("/departments/4"))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
