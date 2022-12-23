package org.example.jdbc.dao;

import java.util.Set;

public abstract class AbstactDAO<T> {

    public abstract boolean create(T t);
    public abstract boolean update(T t);
    public abstract boolean delete(T t);
    public abstract T getById(T t);
    public abstract Set<T> getAll();


}
