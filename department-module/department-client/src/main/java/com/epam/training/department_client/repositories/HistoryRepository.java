package com.epam.training.department_client.repositories;

import com.epam.training.department_client.models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
