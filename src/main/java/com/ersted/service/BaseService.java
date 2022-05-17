package com.ersted.service;

import java.util.List;

public interface BaseService<T, R> {
    T create(T t);

    T getById(R id);

    T update(T t);

    void deleteById(R id);

    List<T> getAll();
}
