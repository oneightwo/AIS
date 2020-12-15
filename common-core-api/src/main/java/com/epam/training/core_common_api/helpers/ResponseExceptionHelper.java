package com.epam.training.core_common_api.helpers;

import com.epam.training.core_common_api.models.ResponseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

@Service
public class ResponseExceptionHelper {

    public String getStringJsonResponseBody(String requestPath, String queryParams, HttpStatus httpStatus, String message) {
        ObjectMapper mapper = new ObjectMapper();
        ResponseException responseException = new ResponseException(
                requestPath,
                Objects.isNull(queryParams) ? "" : queryParams,
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message);
        try {
            return mapper.writeValueAsString(responseException);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseException.toString();
    }

    public ResponseException getResponseBody(String requestPath, String queryParams, HttpStatus httpStatus, String message) {
        return new ResponseException(
                requestPath,
                Objects.isNull(queryParams) ? "" : queryParams,
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message);
    }

    public ResponseException getResponseBody(WebRequest request, HttpStatus httpStatus, Exception ex) {
        return new ResponseException(
                getRequestPath(request),
                getQueryParams(request),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                ex.getLocalizedMessage());
    }

    public ResponseException getResponseBody(WebRequest request, HttpStatus httpStatus, String message) {
        return new ResponseException(
                getRequestPath(request),
                getQueryParams(request),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message);
    }

    public ResponseException getResponseBody(WebRequest request, int statusCode, String message) {
        return new ResponseException(
                getRequestPath(request),
                getQueryParams(request),
                HttpStatus.valueOf(statusCode).value(),
                HttpStatus.valueOf(statusCode).getReasonPhrase(),
                message);
    }

    private String getRequestPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private String getQueryParams(WebRequest request) {
        return StringUtils.defaultIfEmpty(((ServletWebRequest) request).getRequest().getQueryString(), "");
    }

    public String getMessage(FeignException ex) {
        Gson gson = new Gson();
        ResponseException responseException = gson.fromJson(ex.contentUTF8(), ResponseException.class);
        return responseException.getMessage();
    }
}
