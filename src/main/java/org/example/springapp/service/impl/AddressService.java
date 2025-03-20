package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.repository.AddressRepository;
import org.example.springapp.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для выполнения CRUD операций с сущностью {@link  Address}.
 */
@Service
@AllArgsConstructor
public class AddressService implements CRUDService<Address> {
    private final AddressRepository repository;

    /**
     * Создает новый объект Address и сохраняет его в базе данных.
     *
     * @param entity объект Address, который нужно сохранить
     * @return сохранённый объект Address
     */
    @Override
    public Address create(Address entity) {
        return repository.save(entity);
    }

    /**
     * Получает список всех объектов Address из базы данных.
     *
     * @return список всех объектов Address
     */
    @Override
    public List<Address> read() {
        return repository.findAll();
    }

    /**
     * Ищет и возвращает объект Address по его ID.
     *
     * @param id уникальный идентификатор объекта Address
     * @return объект Address с заданным ID
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public Address getByID(Long id) throws ResourceNotFoundException {
        return repository.getAddressById(id).orElseThrow(() -> new ResourceNotFoundException("Address with this id doesn't exist"));
    }

    /**
     * Обновляет существующий объект Address в базе данных.
     *
     * @param entity обновленный объект Address
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void update(Address entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Address with this id doesn't exist");
        }
        repository.save(entity);
    }

    /**
     * Удаляет объект Address из базы данных по его ID.
     *
     * @param id уникальный идентификатор объекта Address
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Address with this id doesn't exist");
        }
        repository.deleteById(id);
    }
}
