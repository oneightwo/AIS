package com.epam.training.zuul_service.config;

import com.epam.training.core_common_api.helpers.ResponseExceptionHelper;
import com.epam.training.zuul_service.constants.ZuulConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ErrorFilter extends ZuulFilter {

    private final ResponseExceptionHelper responseExceptionHelper;

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return Objects.nonNull(RequestContext.getCurrentContext().get("throwable"));
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            ZuulException zuulException = (ZuulException) context.get("throwable");
            if (zuulException.nStatusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                context.remove("throwable");
                context.getResponse().setContentType("application/json");
                context.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                context.setResponseBody(responseExceptionHelper.getStringJsonResponseBody(
                        context.getRequest().getRequestURI(),
                        context.getRequest().getQueryString(),
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ZuulConstants.FAILED_TO_ACCESS + context.get("proxy")
                ));
            }
        } catch (Exception e) {
            throw new ZuulException(e, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }
}
