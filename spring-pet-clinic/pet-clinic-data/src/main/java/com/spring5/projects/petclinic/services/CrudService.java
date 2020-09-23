package com.spring5.projects.petclinic.services;

import java.util.Set;

public interface CrudService<T,ID> {

    T findById(ID Id);

    T save(T t);

    Set<T> findAll();

    void delete(T t);

    void deleteById(ID id);
}
