package com.epam.training.department_client.config.mappers;

import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.department_client.models.Position;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PositionMapper implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory factory) {
        factory.classMap(Position.class, PositionDto.class)
                .byDefault()
                .register();
    }
}
