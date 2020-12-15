package com.epam.training.department_client.services.impl;

import com.epam.training.core_common_api.exceptions.MethodNotSupportedException;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.common_department_api.constants.PositionExceptionConstants;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.department_client.repositories.PositionRepository;
import com.epam.training.department_client.services.PositionService;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final MapperFacade mapperFacade;

    @Override
    public List<PositionDto> getAll() {
        return mapperFacade.mapAsList(positionRepository.findAll(), PositionDto.class);
    }

    @Override
    public PositionDto getById(Long positionId) {
        return mapperFacade.map(positionRepository.findById(positionId)
                .orElseThrow(() -> new ObjectNotFoundException(PositionExceptionConstants.POSITION_WITH_THIS_ID_DOES_NOT_EXIST)), PositionDto.class);
    }
}
