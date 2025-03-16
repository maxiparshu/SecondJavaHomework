package org.example.springapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.dto.ServiceDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Service;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.service.impl.ServiceService;
import org.example.springapp.utils.mapper.EntityByIDMapper;
import org.example.springapp.utils.mapper.ServiceMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
/**
 * Контроллер для управления сервисами.
 * Обрабатывает запросы для создания, обновления, удаления и получения информации о сервисах.
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;
    private final AttractionService attractionService;

    /**
     * Получить все сервисы.
     *
     * @return Список всех сервисов.
     */
    @GetMapping("/all")
    public List<Service> readAll() {
        return serviceService.read();
    }

    /**
     * Получить сервис по его ID.
     *
     * @param ID Идентификатор сервиса.
     * @return ResponseEntity с найденным сервисом.
     * @throws ResourceNotFoundException если сервис с данным ID не найден.
     */
    @GetMapping("find/{id}")
    public ResponseEntity<Service> getServiceById(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(serviceService.getByID(ID), HttpStatus.OK);
    }

    /**
     * Создать новый сервис.
     *
     * @param serviceDTO DTO с данными для создания сервиса.
     * @return HTTP статус CREATED, если сервис успешно создан.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @PostMapping("/create")
    public HttpStatus createService(@Valid @RequestBody ServiceDTO serviceDTO) throws ResourceNotFoundException {
        var entity = serviceMapper.toEntity(serviceDTO);
        if (serviceDTO.getAttractionsID() != null)
            entity.setAttractions(EntityByIDMapper.fetchByIds(serviceDTO.getAttractionsID(), attractionService, "Attraction"));
        else
            entity.setAttractions(new ArrayList<>());
        serviceService.create(entity);
        return HttpStatus.CREATED;
    }

    /**
     * Обновить информацию о существующем сервисе.
     *
     * @param serviceDTO DTO с обновленной информацией о сервисе.
     * @return HTTP статус OK, если сервис успешно обновлен.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @PutMapping("/update")
    public HttpStatus updateService(@Valid @RequestBody ServiceDTO serviceDTO) throws ResourceNotFoundException {
        var entity = serviceMapper.toEntity(serviceDTO);
        if (serviceDTO.getAttractionsID() != null)
            entity.setAttractions(EntityByIDMapper.fetchByIds(serviceDTO.getAttractionsID(), attractionService, "Attraction"));
        else
            entity.setAttractions(new ArrayList<>());
        serviceService.update(entity);
        return HttpStatus.OK;
    }

    /**
     * Удалить сервис по ID.
     *
     * @param ID Идентификатор сервиса для удаления.
     * @return HTTP статус OK, если сервис успешно удален.
     * @throws ResourceNotFoundException если сервис с данным ID не найден.
     */
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteService(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        serviceService.delete(ID);
        return HttpStatus.OK;
    }
}
