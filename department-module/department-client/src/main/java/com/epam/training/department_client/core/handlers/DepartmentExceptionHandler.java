package com.epam.training.department_client.core.handlers;

import com.epam.training.core_common_api.exceptions.InternalErrorGettingResources;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.core_common_api.helpers.ResponseExceptionHelper;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class DepartmentExceptionHandler extends ResponseEntityExceptionHandler {

    private final ResponseExceptionHelper responseExceptionHelper;

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    protected ResponseEntity<?> handleDataIntegrityViolation(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, responseExceptionHelper.getResponseBody(request, HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {HystrixRuntimeException.class})
    protected ResponseEntity<?> handleHystrixRuntime(HystrixRuntimeException ex, WebRequest request) {
        if (ex.getCause() instanceof FeignException) {
            var feignException = (FeignException) ex.getCause();
            var status = HttpStatus.valueOf(feignException.status());
            return handleExceptionInternal(feignException,
                    responseExceptionHelper.getResponseBody(request, status, responseExceptionHelper.getMessage(feignException)),
                    new HttpHeaders(),
                    status,
                    request);
        } else {
            var privateException = (InternalErrorGettingResources) ex.getFallbackException().getCause().getCause();
            return handleExceptionInternal(privateException,
                    responseExceptionHelper.getResponseBody(request, HttpStatus.INTERNAL_SERVER_ERROR, privateException),
                    new HttpHeaders(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    request);
        }
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    protected ResponseEntity<?> handleObjectNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, responseExceptionHelper.getResponseBody(request, HttpStatus.BAD_REQUEST, ex), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
