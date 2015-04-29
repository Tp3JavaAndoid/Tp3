package cem.keven.pelletier.cashapp;

import java.util.List;

/**
 * Created by joris on 15-03-20.
 */
public interface CRUD<T> {

    long save(T o);

    void saveMany(Iterable<T> list);

    void saveMany(T... list);

    T getById(Long p);

    List<T> getAll();

    void deleteOne(Long o);

    void deleteOne(T o);

    void deleteAll();
}
