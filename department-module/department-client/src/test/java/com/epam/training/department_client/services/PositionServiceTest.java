package com.epam.training.department_client.services;

import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.core_common_api.exceptions.MethodNotSupportedException;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.department_client.data.PositionTestDataProvider;
import com.epam.training.department_client.models.Position;
import com.epam.training.department_client.repositories.PositionRepository;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Autowired
    private PositionService positionService;

    @MockBean
    private MapperFacade mapper;
    @MockBean
    private PositionRepository positionRepository;


    @Test
    void getAll() {
        List<Position> allPositions = PositionTestDataProvider.getAllPositions();
        when(positionRepository.findAll())
                .thenReturn(allPositions);
        List<PositionDto> positionDtoList = PositionTestDataProvider.map(allPositions);
        when(mapper.mapAsList(anyList(), eq(PositionDto.class)))
                .thenReturn(positionDtoList);
        assertEquals(positionDtoList, positionService.getAll());
    }

    @Test
    void save() {
        assertThrows(MethodNotSupportedException.class,
                () -> positionService.save(PositionTestDataProvider
                        .map(PositionTestDataProvider.getPosition(PositionTestDataProvider.FIRST_POSITION_ID))));
    }

    @Test
    void update() {
        assertThrows(MethodNotSupportedException.class,
                () -> positionService.update(PositionTestDataProvider
                        .map(PositionTestDataProvider.getPosition(PositionTestDataProvider.FIRST_POSITION_ID))));
    }

    @Test
    void delete() {
        assertThrows(MethodNotSupportedException.class,
                () -> positionService.delete(PositionTestDataProvider.FIRST_POSITION_ID));
    }

    @Test
    void getByIdWithCorrectId() {
        assertEquals(getTestDataForGetById(), positionService.getById(PositionTestDataProvider.FIRST_POSITION_ID));
    }

    @Test
    void getByIdWithIncorrectId() {
        getTestDataForGetById();
        assertThrows(ObjectNotFoundException.class,
                () -> positionService.getById(PositionTestDataProvider.INCORRECT_POSITION_ID));
    }

    private PositionDto getTestDataForGetById() {
        Position position = PositionTestDataProvider.getPosition(PositionTestDataProvider.FIRST_POSITION_ID);
        when(positionRepository.findById(PositionTestDataProvider.FIRST_POSITION_ID))
                .thenReturn(Optional.of(position));
        PositionDto positionDto = PositionTestDataProvider.map(position);
        when(mapper.map(any(), eq(PositionDto.class)))
                .thenReturn(positionDto);
        return positionDto;
    }
}