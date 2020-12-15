package com.epam.training.ui_server.repositories;

import com.epam.training.ui_server.models.DepartmentSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentSnapshot, Long> {

    Optional<DepartmentSnapshot> findByName(String name);

}

