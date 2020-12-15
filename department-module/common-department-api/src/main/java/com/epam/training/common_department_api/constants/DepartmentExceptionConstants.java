package com.epam.training.common_department_api.constants;

public interface DepartmentExceptionConstants {
    String DEPARTMENT_WITH_THE_SAME_NAME_ALREADY_EXISTS = "Department with the same name already exists";
    String DEPARTMENT_WITH_THIS_NAME_DOES_NOT_EXIST = "Department with this name doesn't exist";
    String DEPARTMENT_CURRENT_DEPARTMENT_ID_WITH_THIS_ID_DOES_NOT_EXIST = "Department 'current_department_id' with this id doesn't exist";
    String DEPARTMENT_DESTINATION_DEPARTMENT_ID_WITH_THIS_ID_DOES_NOT_EXIST = "Department 'destination_department_id' with this id doesn't exist";
    String DEPARTMENT_ID_TO_MUST_NOT_BE_EQUAL_TO_DEPARTMENT_ID_FROM = "Department id 'to' must not be equal to department id 'from'";
    String REMOVAL_IS_NOT_POSSIBLE_THE_DEPARTMENT_HAS_EMPLOYEES = "Removal is not possible. The department has employees";
    String THE_DEPARTMENT_HAS_DEPENDENT_DEPARTMENTS = "The department has dependent departments";
    String DEPARTMENT_WITH_THIS_ID_DOES_NOT_EXIST = "Department with this id doesn't exist";
    String ERROR_WHEN_GETTING_A_DEPARTMENT = "Error when getting a department";
    String ERROR_WHEN_GETTING_A_POSITION = "Error when getting a position";
}