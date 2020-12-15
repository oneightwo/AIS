package com.epam.training.common_employee_api.constants;

public interface EmployeeExceptionConstants {
    String EMPLOYEE_WITH_THIS_ID_DOES_NOT_EXIST = "Employee with this id doesn't exist";
    String INVALID_DATE = "Invalid date";
    String THE_DEPARTMENT_ALREADY_HAS_A_LEADER = "The department already has a leader";
    String THE_EMPLOYEE_SALARY_CANNOT_BE_MORE_THAN_THE_BOSS_SALARY = "The employee's salary cannot be more than the boss's salary";
    String ERROR_GETTING_A_LEADER_IN_THE_DEPARTMENT = "Error getting a leader in the department";
    String ERROR_GETTING_DEPARTMENT_EMPLOYEES = "Error getting department employees";
    String THE_BOSS_SALARY_CANNOT_BE_LESS_THAN_THE_EMPLOYEE_SALARY = "The boss's salary cannot be less than the employee's salary";
}
