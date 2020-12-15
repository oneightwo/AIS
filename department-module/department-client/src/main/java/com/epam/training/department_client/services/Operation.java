package com.epam.training.department_client.services;

import com.epam.training.department_client.models.Department;

@FunctionalInterface
public interface Operation {
    Department action();
}
