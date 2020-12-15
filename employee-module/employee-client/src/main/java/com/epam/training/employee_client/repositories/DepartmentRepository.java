package com.epam.training.employee_client.repositories;

import com.epam.training.employee_client.models.DepartmentSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentSnapshot, Long> {

}
