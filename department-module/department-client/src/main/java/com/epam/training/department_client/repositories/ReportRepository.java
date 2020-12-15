package com.epam.training.department_client.repositories;

import com.epam.training.department_client.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByDepartmentId(Long departmentId);
}
