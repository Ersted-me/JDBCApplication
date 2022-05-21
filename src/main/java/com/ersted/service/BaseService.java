package com.ersted.service;

import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;

import java.util.List;

public interface BaseService<T, R> {
    T create(T t);

    T getById(R id) throws NotFoundException;

    T update(T t) throws AlreadyExistException;

    void deleteById(R id);

    List<T> getAll();
}
