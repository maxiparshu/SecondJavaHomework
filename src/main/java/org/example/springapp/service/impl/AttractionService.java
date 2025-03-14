package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.repository.AttractionRepository;
import org.example.springapp.service.CRUDService;
import org.example.springapp.utils.enums.ServiceType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для выполнения CRUD операций с сущностью {@link Attraction}.
 */
@Service
@AllArgsConstructor
public class AttractionService implements CRUDService<Attraction> {
    private final AttractionRepository repository;

    /**
     * Создает новый объект {@link Attraction} и сохраняет его в базе данных.
     *
     * @param entity объект {@link Attraction}, который нужно сохранить
     * @return сохранённый объект {@link Attraction}
     */
    @Override
    public Attraction create(Attraction entity) {
        return repository.save(entity);
    }

    /**
     * Получает список всех объектов {@link Attraction} из базы данных.
     *
     * @return список всех объектов {@link Attraction}
     */
    @Override
    public List<Attraction> read() {
        return repository.findAll();
    }

    /**
     * Ищет и возвращает объект {@link Attraction} по его ID.
     *
     * @param id уникальный идентификатор объекта {@link Attraction}
     * @return объект {@link Attraction} с заданным ID
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public Attraction getByID(Long id) throws ResourceNotFoundException {
        return repository.getAttractionById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attraction with this id doesn't exist"));
    }

    /**
     * Обновляет существующий объект {@link Attraction} в базе данных.
     *
     * @param entity обновлённый объект {@link Attraction}
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
     * Удаляет объект {@link Attraction} из базы данных по его ID.
     *
     * @param id уникальный идентификатор объекта {@link Attraction}
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Attraction with this id doesn't exist");
        }
        repository.deleteById(id);
    }

    /**
     * Находит достопримечательности по фрагменту названия.
     *
     * @param name фрагмент названия достопримечательности
     * @return список достопримечательностей, чьи названия содержат указанный фрагмент, игнорируя регистр
     */
    public List<Attraction> findByNameContaining(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    /**
     * Находит достопримечательности по городу.
     *
     * @param city город, в котором расположены достопримечательности
     * @return список достопримечательностей, расположенных в указанном городе
     */
    public List<Attraction> searchAttractionsByCity(String city) {
        return repository.findByAddressCity(city);
    }

    /**
     * Находит достопримечательности по региону.
     *
     * @param region регион, в котором расположены достопримечательности
     * @return список достопримечательностей, расположенных в указанном регионе
     */
    public List<Attraction> searchAttractionsByRegion(String region) {
        return repository.findByAddressRegion(region);
    }

    /**
     * Находит достопримечательности по типу услуги.
     *
     * @param serviceName тип услуги, предоставляемой достопримечательностью
     * @return список достопримечательностей, предоставляющих указанную услугу
     */
    public List<Attraction> searchAttractionsByService(ServiceType serviceName) {
        return repository.findByServicesServiceType(serviceName);
    }
}
