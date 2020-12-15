package com.epam.training.zuul_service.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

@Primary
@Component
public class SwaggerConfig implements SwaggerResourcesProvider {

    @Override
    public List<SwaggerResource> get() {
        return List.of(swaggerResource("department-server", "/v2/api-docs", "2.0"),
                swaggerResource("employee-server", "/v2/api-docs", "2.0"));
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
