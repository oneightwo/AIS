package com.epam.training.department_client.services;

import com.epam.training.department_client.constants.HistoryType;
import com.epam.training.department_client.models.Department;

public interface DepartmentHistory {

    void save(Department department, HistoryType type);

    void update(Department updatableDepartment, Operation saveDepartment);

}
