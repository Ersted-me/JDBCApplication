package com.ersted.service;

import java.util.List;

public interface BaseService<T, R> {
    T create(T t);

    T getById(R id) throws Exception;

    T update(T t) throws Exception;

    void deleteById(R id);

    List<T> getAll();
}
