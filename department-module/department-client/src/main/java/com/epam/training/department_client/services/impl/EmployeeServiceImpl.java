package com.epam.training.department_client.services.impl;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.department_client.repositories.EmployeeRepository;
import com.epam.training.department_client.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final MapperFacade mapper;

    @Override
    public List<EmployeeDto> getAll() {
        return mapper.mapAsList(employeeRepository.findAll(), EmployeeDto.class);
    }
}
