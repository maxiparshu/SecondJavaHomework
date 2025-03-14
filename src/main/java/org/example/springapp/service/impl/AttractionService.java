package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.repository.AttractionRepository;
import org.example.springapp.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для выполнения CRUD операций с сущностью Attraction.
 */
@Service
@AllArgsConstructor
public class AttractionService implements CRUDService<Attraction> {
    private final AttractionRepository repository;

    /**
     * Создает новый объект Attraction и сохраняет его в базе данных.
     *
     * @param entity объект Attraction, который нужно сохранить
     * @return сохранённый объект Attraction
     */
    @Override
    public Attraction create(Attraction entity) {
        return repository.save(entity);
    }

    /**
     * Получает список всех объектов Attraction из базы данных.
     *
     * @return список всех объектов Attraction
     */
    @Override
    public List<Attraction> read() {
        return repository.findAll();
    }

    /**
     * Ищет и возвращает объект Attraction по его ID.
     *
     * @param id уникальный идентификатор объекта Attraction
     * @return объект Attraction с заданным ID
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public Attraction getByID(Long id) throws ResourceNotFoundException {
        return repository.getAttractionById(id).orElseThrow(() -> new ResourceNotFoundException("Attraction with this id doesn't exist"));
    }

    /**
     * Обновляет существующий объект Attraction в базе данных.
     *
     * @param entity обновленный объект Attraction
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void update(Attraction entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Attraction with this id doesn't exist");
        }
        repository.save(entity);
    }

    /**
     * Удаляет объект Attraction из базы данных по его ID.
     *
     * @param id уникальный идентификатор объекта Attraction
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Attraction with this id doesn't exist");
        }
        repository.deleteById(id);
    }
    public List<Attraction> findByNameContaining(String name){
        return repository.findByNameContainingIgnoreCase(name);
    }
    public List<Attraction> searchAttractionsByCity(String city) {
        return repository.findByAddressCity(city);
    }

    // Поиск по региону
    public List<Attraction> searchAttractionsByRegion(String region) {
        return repository.findByAddressRegion(region);
    }
}
