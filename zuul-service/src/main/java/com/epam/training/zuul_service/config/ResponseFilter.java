package com.epam.training.zuul_service.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.InputStream;

@Configuration
public class ResponseFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        try {
            InputStream responseDataStream = context.getResponseDataStream();
            if (responseDataStream == null) {
                return null;
            }
            context.setResponse(context.getResponse());
        } catch (Exception e) {
            throw new ZuulException(e, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return null;
    }
}
