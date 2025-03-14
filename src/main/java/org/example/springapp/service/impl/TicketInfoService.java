package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.repository.TicketInfoRepository;
import org.example.springapp.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для выполнения CRUD операций с сущностью {@link TicketInfo}.
 */
@Service
@AllArgsConstructor
public class TicketInfoService implements CRUDService<TicketInfo> {
    private final TicketInfoRepository repository;

    /**
     * Создает новый объект TicketInfo и сохраняет его в базе данных.
     *
     * @param entity объект TicketInfo, который нужно сохранить
     * @return сохранённый объект TicketInfo
     */
    @Override
    public TicketInfo create(TicketInfo entity) {
        return repository.save(entity);
    }

    /**
     * Получает список всех объектов TicketInfo из базы данных.
     *
     * @return список всех объектов TicketInfo
     */
    @Override
    public List<TicketInfo> read() {
        return repository.findAll();
    }

    /**
     * Ищет и возвращает объект TicketInfo по его ID.
     *
     * @param id уникальный идентификатор объекта TicketInfo
     * @return объект TicketInfo с заданным ID
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public TicketInfo getByID(Long id) throws ResourceNotFoundException {
        return repository.getTicketInfoById(id).orElseThrow(()
                -> new ResourceNotFoundException("Ticket info with this id doesn't exist"));
    }

    /**
     * Обновляет существующий объект TicketInfo в базе данных.
     *
     * @param entity обновленный объект TicketInfo
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void update(TicketInfo entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Ticket info with this id doesn't exist");
        }
        repository.save(entity);
    }

    /**
     * Удаляет объект TicketInfo из базы данных по его ID.
     *
     * @param id уникальный идентификатор объекта TicketInfo
     * @throws ResourceNotFoundException если объект с таким ID не найден
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket info with this id doesn't exist");
        }
        repository.deleteById(id);
    }
}
