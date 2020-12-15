package com.epam.training.employee_client.data;

import com.epam.training.common_department_api.dto.PositionDto;

public class PositionTestDataProvider {

    public static final String POSITION_NAME = "POSITION_NAME";

    public static final Long INCORRECT_POSITION_ID = 182L;
    public static final Long FIRST_POSITION_ID = 1L;
    public static final Long SECOND_POSITION_ID = 1L;
    public static final Long THIRD_POSITION_ID = 1L;

//    public static List<Position> getAllPositions() {
//        return List.of(getPosition(FIRST_POSITION_ID),
//                getPosition(SECOND_POSITION_ID),
//                getPosition(THIRD_POSITION_ID));
//    }
//
//    public static Position getPosition(Long positionId) {
//        var positionDto = new Position();
//        positionDto.setId(positionId);
//        positionDto.setName("POSITION_NAME_" + positionId);
//        return positionDto;
//    }

    public static PositionDto getPositionDto(Long positionId) {
        var positionDto = new PositionDto();
        positionDto.setId(positionId);
        positionDto.setName("POSITION_NAME_" + positionId);
        return positionDto;
    }

//    public static List<PositionDto> map(List<Position> positionList) {
//        return positionList.stream()
//                .map(PositionTestDataProvider::map)
//                .collect(Collectors.toList());
//    }
//
//    public static PositionDto map(Position position) {
//        var positionDto = new PositionDto();
//        positionDto.setId(position.getId());
//        positionDto.setName(position.getName());
//        return positionDto;
//    }
}
