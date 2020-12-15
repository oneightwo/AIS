package com.epam.training.department_client.data;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.models.Gender;
import com.epam.training.department_client.models.EmployeeSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public class EmployeeTestDataProvider {

    public static final Long EMPLOYEE_LEADER_ID = 1L;
    public static final Long EMPLOYEE_ID = 2L;
    public static final Long EMPLOYEE_LEADER_POSITION_ID = 1L;
    public static final Long EMPLOYEE_POSITION_ID = 2L;
    public static final Double EMPLOYEE_SALARY = 60000.0;
    public static final Double EMPLOYEE_LEADER_SALARY = 150000.0;

    public static EmployeeDto getEmployeeLeaderDto() {
        var employeeLeaderDto = getEmployeeDto();
        employeeLeaderDto.setId(EMPLOYEE_LEADER_ID);
        employeeLeaderDto.setPositionId(EMPLOYEE_LEADER_POSITION_ID);
        employeeLeaderDto.setSalary(EMPLOYEE_LEADER_SALARY);
        employeeLeaderDto.setDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        employeeLeaderDto.setIsLeader(true);
        return employeeLeaderDto;
    }

    public static EmployeeDto getEmployeeDto() {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(EMPLOYEE_ID);
        employeeDto.setSurname("Иванов");
        employeeDto.setName("Иван");
        employeeDto.setPatronymic("Иванович");
        employeeDto.setGender(Gender.MALE);
        employeeDto.setDateOfBirth(LocalDate.of(1980, 6, 21));
        employeeDto.setEmail("test@gmail.com");
        employeeDto.setPhone("88005553535");
        employeeDto.setEmploymentDate(LocalDate.of(2010, 4, 1));
        employeeDto.setPositionId(EMPLOYEE_POSITION_ID);
        employeeDto.setSalary(EMPLOYEE_SALARY);
        employeeDto.setDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        employeeDto.setIsLeader(true);
        return employeeDto;
    }

    public static EmployeeSnapshot map(EmployeeDto employeeDto) {
        var employeeSnapshot = new EmployeeSnapshot();
        employeeSnapshot.setId(employeeDto.getId());
        employeeSnapshot.setSurname(employeeDto.getSurname());
        employeeSnapshot.setName(employeeDto.getName());
        employeeSnapshot.setPatronymic(employeeDto.getPatronymic());
        employeeSnapshot.setGender(employeeDto.getGender());
        employeeSnapshot.setDateOfBirth(employeeDto.getDateOfBirth());
        employeeSnapshot.setEmail(employeeDto.getEmail());
        employeeSnapshot.setPhone(employeeDto.getPhone());
        employeeSnapshot.setEmploymentDate(employeeDto.getEmploymentDate());
        employeeSnapshot.setPosition(PositionTestDataProvider.getPosition(employeeDto.getPositionId()));
        employeeSnapshot.setSalary(employeeDto.getSalary());
        employeeSnapshot.setDepartment(DepartmentTestDataProvider.getDepartmentWithChildren(employeeDto.getDepartmentId()));
        employeeSnapshot.setIsLeader(employeeDto.getIsLeader());
        return employeeSnapshot;
    }
}
