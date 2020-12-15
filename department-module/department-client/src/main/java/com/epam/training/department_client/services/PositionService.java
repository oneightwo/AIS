package com.epam.training.department_client.services;

import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.core_common_api.services.CRUDService;
import com.epam.training.department_client.models.Position;

import java.util.List;

public interface PositionService extends CRUDService<PositionDto, Long> {

    List<PositionDto> getAll();
}
