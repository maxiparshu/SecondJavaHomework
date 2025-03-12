package org.example.springapp.service;

import org.example.springapp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CRUDService<T> {
    T create(T entity);

    List<T> read();

    T getByID(Long id) throws ResourceNotFoundException;

    void update(T entity) throws ResourceNotFoundException;

    void delete(Long id) throws ResourceNotFoundException;

}
