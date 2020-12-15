package com.epam.training.employee_client.data;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.models.Gender;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.models.Employee;

import java.time.LocalDate;
import java.util.List;

public class EmployeeTestDataProvider {

    public static final Long EMPLOYEE_LEADER_ID = 1L;
    public static final Long EMPLOYEE_ID = 2L;
    public static final Long EMPLOYEE_LEADER_POSITION_ID = 1L;
    public static final Long EMPLOYEE_POSITION_ID = 2L;
    public static final Double EMPLOYEE_SALARY = 60000.0;
    public static final Double EMPLOYEE_LEADER_SALARY = 150000.0;
    public static final Long INCORRECT_EMPLOYEE_ID = 182L;
    public static final LocalDate EMPLOYEE_DATE_OF_BIRTH = LocalDate.of(1980, 6, 21);
    public static final LocalDate EMPLOYEE_DISMISSAL_DATE = LocalDate.of(1980, 6, 21);
    public static final LocalDate EMPLOYEE_EMPLOYMENT_DATE = LocalDate.of(1980, 6, 21);

//    public static EmployeeDto getEmployeeLeaderDto() {
//        var employeeLeaderDto = getEmployeeDto();
//        employeeLeaderDto.setId(EMPLOYEE_LEADER_ID);
//        employeeLeaderDto.setPositionId(EMPLOYEE_LEADER_POSITION_ID);
//        employeeLeaderDto.setSalary(EMPLOYEE_LEADER_SALARY);
//        employeeLeaderDto.setDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
//        employeeLeaderDto.setIsLeader(true);
//        return employeeLeaderDto;
//    }

    public static EmployeeDto getEmployeeLeaderDto() {
        var employeeLeader = getEmployeeLeader();
        return map(employeeLeader);
    }

    public static Employee getEmployeeLeader() {
        var employeeLeader = getEmployee();
        employeeLeader.setId(EMPLOYEE_LEADER_ID);
        employeeLeader.setPositionId(EMPLOYEE_LEADER_POSITION_ID);
        employeeLeader.setSalary(EMPLOYEE_LEADER_SALARY);
        employeeLeader.setDepartment(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
        employeeLeader.setIsLeader(true);
        return employeeLeader;
    }

    public static DismissalEmployeeDto getDismissalEmployeeDto() {
        EmployeeDto employeeDto = getEmployeeDto();
        var dismissalEmployeeDto = new DismissalEmployeeDto();
        dismissalEmployeeDto.setId(employeeDto.getId());
        dismissalEmployeeDto.setSurname(employeeDto.getSurname());
        dismissalEmployeeDto.setName(employeeDto.getName());
        dismissalEmployeeDto.setPatronymic(employeeDto.getPatronymic());
        dismissalEmployeeDto.setGender(employeeDto.getGender());
        dismissalEmployeeDto.setDateOfBirth(employeeDto.getDateOfBirth());
        dismissalEmployeeDto.setDateOfDismissal(EMPLOYEE_DISMISSAL_DATE);
        dismissalEmployeeDto.setEmail(employeeDto.getEmail());
        dismissalEmployeeDto.setPhone(employeeDto.getPhone());
        dismissalEmployeeDto.setEmploymentDate(employeeDto.getEmploymentDate());
        dismissalEmployeeDto.setPositionId(employeeDto.getPositionId());
        dismissalEmployeeDto.setSalary(employeeDto.getSalary());
        dismissalEmployeeDto.setDepartmentId(employeeDto.getDepartmentId());
        dismissalEmployeeDto.setIsLeader(employeeDto.getIsLeader());
        return dismissalEmployeeDto;
    }

//    public static EmployeeDto getEmployeeDtoWithDepartment() {
//        var employeeDto = new EmployeeDto();
//        employeeDto.setId(EMPLOYEE_ID);
//        employeeDto.setSurname("Иванов");
//        employeeDto.setName("Иван");
//        employeeDto.setPatronymic("Иванович");
//        employeeDto.setGender(Gender.MALE);
//        employeeDto.setDateOfBirth(EMPLOYEE_DATE_OF_BIRTH);
//        employeeDto.setEmail("test@gmail.com");
//        employeeDto.setPhone("88005553535");
//        employeeDto.setEmploymentDate(EMPLOYEE_EMPLOYMENT_DATE);
//        employeeDto.setPositionId(EMPLOYEE_POSITION_ID);
//        employeeDto.setSalary(EMPLOYEE_SALARY);
//        employeeDto.setDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
//        employeeDto.setIsLeader(false);
//        return employeeDto;
//    }

    public static EmployeeDto getEmployeeDto() {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(EMPLOYEE_ID);
        employeeDto.setSurname("Иванов");
        employeeDto.setName("Иван");
        employeeDto.setPatronymic("Иванович");
        employeeDto.setGender(Gender.MALE);
        employeeDto.setDateOfBirth(EMPLOYEE_DATE_OF_BIRTH);
        employeeDto.setEmail("test@gmail.com");
        employeeDto.setPhone("88005553535");
        employeeDto.setEmploymentDate(EMPLOYEE_EMPLOYMENT_DATE);
        employeeDto.setPositionId(EMPLOYEE_POSITION_ID);
        employeeDto.setSalary(EMPLOYEE_SALARY);
        employeeDto.setDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        employeeDto.setIsLeader(false);
        return employeeDto;
    }

    public static Employee getEmployee() {
        EmployeeDto employeeDto = getEmployeeDto();
        var employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setSurname(employeeDto.getSurname());
        employee.setName(employeeDto.getName());
        employee.setPatronymic(employeeDto.getPatronymic());
        employee.setGender(employeeDto.getGender());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmploymentDate(employeeDto.getEmploymentDate());
        employee.setPositionId(employeeDto.getPositionId());
        employee.setSalary(employeeDto.getSalary());
        employee.setDepartment(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(employeeDto.getDepartmentId()));
        employee.getDepartment().setEmployees(List.of(employee));
        employee.setIsLeader(employeeDto.getIsLeader());
        return employee;
    }

    public static Employee map(EmployeeDto employeeDto) {
        var employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setSurname(employeeDto.getSurname());
        employee.setName(employeeDto.getName());
        employee.setPatronymic(employeeDto.getPatronymic());
        employee.setGender(employeeDto.getGender());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setEmploymentDate(employeeDto.getEmploymentDate());
        employee.setPositionId(employeeDto.getPositionId());
        employee.setSalary(employeeDto.getSalary());
        employee.setDepartment(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(employeeDto.getDepartmentId()));
        employee.getDepartment().setEmployees(List.of(employee));
        employee.setIsLeader(employeeDto.getIsLeader());
        return employee;
    }

    public static EmployeeDto map(Employee employee) {
        var employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setSurname(employee.getSurname());
        employeeDto.setName(employee.getName());
        employeeDto.setPatronymic(employee.getPatronymic());
        employeeDto.setGender(employee.getGender());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setDateOfDismissal(employee.getDateOfDismissal());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setEmploymentDate(employee.getEmploymentDate());
        employeeDto.setPositionId(employee.getPositionId());
        employeeDto.setSalary(employee.getSalary());
        employeeDto.setDepartmentId(employee.getDepartment().getId());
        employeeDto.setIsLeader(employee.getIsLeader());
        return employeeDto;
    }

    public static Employee map(DismissalEmployeeDto dismissalEmployeeDto) {
        var employee = new Employee();
        employee.setId(dismissalEmployeeDto.getId());
        employee.setSurname(dismissalEmployeeDto.getSurname());
        employee.setName(dismissalEmployeeDto.getName());
        employee.setPatronymic(dismissalEmployeeDto.getPatronymic());
        employee.setGender(dismissalEmployeeDto.getGender());
        employee.setDateOfBirth(dismissalEmployeeDto.getDateOfBirth());
        employee.setDateOfDismissal(dismissalEmployeeDto.getDateOfDismissal());
        employee.setEmail(dismissalEmployeeDto.getEmail());
        employee.setPhone(dismissalEmployeeDto.getPhone());
        employee.setEmploymentDate(dismissalEmployeeDto.getEmploymentDate());
        employee.setPositionId(dismissalEmployeeDto.getPositionId());
        employee.setSalary(dismissalEmployeeDto.getSalary());
        employee.setDepartment(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(dismissalEmployeeDto.getDepartmentId()));
        employee.getDepartment().setEmployees(List.of(employee));
        employee.setIsLeader(dismissalEmployeeDto.getIsLeader());
        return employee;
    }
}
