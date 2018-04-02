package repository;

import model.HasId;

import java.util.Optional;

public interface Repository<Id,T extends HasId<Id>> {
    void save(T elem);
    int size();
    void delete(T elem);
    void put(T elem);
    Iterable<T> getAll();
    T findById(Id id);
}
