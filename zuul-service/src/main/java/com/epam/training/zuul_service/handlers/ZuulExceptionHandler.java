package com.epam.training.zuul_service.handlers;

import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.core_common_api.helpers.ResponseExceptionHelper;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ZuulExceptionHandler extends ResponseEntityExceptionHandler {

    private final ResponseExceptionHelper responseExceptionHelper;

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, responseExceptionHelper.getResponseBody(request, status, ex), headers, status, request);
    }

}
