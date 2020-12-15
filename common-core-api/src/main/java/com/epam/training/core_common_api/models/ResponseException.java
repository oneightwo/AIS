package com.epam.training.core_common_api.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseException {
    private String path;
    private String args;
    private Integer status;
    private String error;
    private String message;
}
