package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Service;
import org.example.springapp.repository.ServiceRepository;
import org.example.springapp.service.CRUDService;

import java.util.List;

/**
 * Сервисный класс для выполнения CRUD операций с сущностью {@link Service}.
 */
@AllArgsConstructor
@org.springframework.stereotype.Service
public class ServiceService implements CRUDService<Service> {
    private final ServiceRepository repository;

    /**
     * Создает новый объект Service и сохраняет его в базе данных.
     *
     * @param entity объект Service, который нужно сохранить
     * @return сохранённый объект Service
     */
    @Override
    public Service create(Service entity) {
        return repository.save(entity);
    }

    /**
     * Получает список всех объектов Service из базы данных.
     *
     * @return список всех объектов Service
     */
    @Override
    public List<Service> read() {
        return repository.findAll();
    }

    /**
     * Ищет и возвращает объект Service по его ID.
     *
     * @param id уникальный идентификатор объекта Service
     * @return объект Service с заданным ID
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public Service getByID(Long id) throws ResourceNotFoundException {
        return repository.getServiceById(id).orElseThrow(()
                -> new ResourceNotFoundException("Service with this id doesn't exist"));
    }

    /**
     * Обновляет существующий объект Service в базе данных.
     *
     * @param entity обновленный объект Service
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void update(Service entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Service with this id doesn't exist");
        }
        repository.save(entity);
    }

    /**
     * Удаляет объект Service из базы данных по его ID.
     *
     * @param id уникальный идентификатор объекта Service
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Service with this id doesn't exist");
        }
        repository.deleteById(id);
    }
}
