package app.dao;

import java.util.Set;

public interface IDAO <T> {
    T create(T t);

    T getById(long id);

    T update(T t);

    T delete(long id);

    Set<T> getAll();
}