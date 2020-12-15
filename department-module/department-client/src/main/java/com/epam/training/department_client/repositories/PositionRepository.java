package com.epam.training.department_client.repositories;

import com.epam.training.department_client.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

}
