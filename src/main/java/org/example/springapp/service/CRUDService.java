package org.example.springapp.service;

import org.example.springapp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Универсальный интерфейс для CRUD операций.
 * Позволяет создавать, читать, обновлять и удалять сущности.
 * @param <T> тип сущности, с которой работает сервис
 */
@Repository
public interface CRUDService<T> {
    /**
     * Создает новую сущность.
     *
     * @param entity сущность, которую нужно создать
     * @return созданная сущность
     */
    T create(T entity);

    /**
     * Считывает и возвращает список всех сущностей.
     *
     * @return список всех сущностей
     */
    List<T> read();

    /**
     * Получает сущность по её ID.
     *
     * @param id уникальный идентификатор сущности
     * @return сущность с заданным ID
     * @throws ResourceNotFoundException если сущность с таким ID не найдена
     */
    T getByID(Long id) throws ResourceNotFoundException;

    /**
     * Обновляет существующую сущность.
     *
     * @param entity обновлённая версия сущности
     * @throws ResourceNotFoundException если сущность с таким ID не найдена
     */
    void update(T entity) throws ResourceNotFoundException;

    /**
     * Удаляет сущность по её ID.
     *
     * @param id уникальный идентификатор сущности
     * @throws ResourceNotFoundException если сущность с таким ID не найдена
     */
    void delete(Long id) throws ResourceNotFoundException;

}
