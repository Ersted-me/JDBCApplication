package com.ersted.controller;

import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;

import java.util.List;

public interface BaseController<T, R> {

    T create(T obj);

    T getById(R id) throws NotFoundException;

    T update(T obj) throws AlreadyExistException;

    void deleteById(R id);

    List<T> getAll();
}
