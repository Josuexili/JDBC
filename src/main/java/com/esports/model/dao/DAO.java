package com.esports.model.dao;

import java.util.List;

public interface DAO<T> {
    void add(T item);

    List<T> getAll();
}
