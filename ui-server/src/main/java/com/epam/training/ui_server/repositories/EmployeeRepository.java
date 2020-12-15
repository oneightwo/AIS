package com.epam.training.ui_server.repositories;

import com.epam.training.ui_server.models.EmployeeSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeSnapshot, Long> {

    List<EmployeeSnapshot> findAllByDepartmentId(long departmentId);

    Optional<EmployeeSnapshot> findEmployeeByDepartmentIdAndIsLeaderTrue(long departmentId);
}
