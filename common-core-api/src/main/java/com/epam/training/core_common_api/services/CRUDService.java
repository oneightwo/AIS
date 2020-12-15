package com.epam.training.core_common_api.services;

import com.epam.training.core_common_api.exceptions.MethodNotSupportedException;

public interface CRUDService<T, ID> {

    default T getById(ID id) {
        throw new MethodNotSupportedException("get by id is not supported yet");
    }

    default T save(T t) {
        throw new MethodNotSupportedException("save is not supported yet");
    }

    default T update(T t) {
        throw new MethodNotSupportedException("update is not supported yet");
    }

    default void delete(ID id) {
        throw new MethodNotSupportedException("delete is not supported yet");
    }
}
