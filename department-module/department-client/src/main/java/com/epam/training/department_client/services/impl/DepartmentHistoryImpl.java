package com.epam.training.department_client.services.impl;

import com.epam.training.common_department_api.dto.DepartmentWithoutChildrenDto;
import com.epam.training.department_client.constants.HistoryType;
import com.epam.training.department_client.models.Department;
import com.epam.training.department_client.models.History;
import com.epam.training.department_client.repositories.HistoryRepository;
import com.epam.training.department_client.services.DepartmentHistory;
import com.epam.training.department_client.services.Operation;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentHistoryImpl implements DepartmentHistory {

    private final HistoryRepository historyRepository;
    private final MapperFacade mapper;

    @Override
    public void save(Department department, HistoryType type) {
        var history = new History();
        history.setType(type);
        if (type.equals(HistoryType.DELETE)) {
            history.setOldDepartment(mapper.map(department, DepartmentWithoutChildrenDto.class));
            historyRepository.save(history);
        } else if (type.equals(HistoryType.CREATE)) {
            history.setNewDepartment(mapper.map(department, DepartmentWithoutChildrenDto.class));
            historyRepository.save(history);
        }
    }

    @Override
    public void update(Department updatableDepartment, Operation saveDepartment) {
        var history = new History();
        history.setType(HistoryType.UPDATE);
        history.setOldDepartment(mapper.map(updatableDepartment, DepartmentWithoutChildrenDto.class));
        history.setNewDepartment(mapper.map(saveDepartment.action(), DepartmentWithoutChildrenDto.class));
        historyRepository.save(history);
    }

}
