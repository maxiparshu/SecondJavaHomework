package org.example.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.aspect.AspectAnnotation;
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
 * Контроллер для управления услугами.
 * Обрабатывает запросы для создания, обновления, удаления и получения информации об услугах.
 */
@AllArgsConstructor
@RestController
@Tag(name = "Service Controller", description = "Управление услугами")
@RequestMapping("api/services")
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;
    private final AttractionService attractionService;

    /**
     * Получить все услуги.
     *
     * @return Список всех услуг.
     */
    @Operation(summary = "Получить все услуги", description = "Возвращает список всех доступных услуг")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @GetMapping("/all")
    public List<Service> readAll() {
        return serviceService.read();
    }

    /**
     * Получить услугу по его ID.
     *
     * @param ID Идентификатор услуги.
     * @return ResponseEntity с найденным услугой.
     * @throws ResourceNotFoundException если услугой с данным ID не найден.
     */
    @Operation(summary = "Получить услугу по ID", description = "Возвращает услугу по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Услуга найден"),
            @ApiResponse(responseCode = "404", description = "Услуга не найден")
    })
    @GetMapping("find/{id}")
    @AspectAnnotation
    public ResponseEntity<Service> getServiceById(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(serviceService.getByID(ID), HttpStatus.OK);
    }

    /**
     * Создать новый услугу.
     *
     * @param serviceDTO DTO с данными для создания услуг.
     * @return HTTP статус CREATED, если услуга успешно создан.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @Operation(summary = "Создать новый услугу", description = "Создает новую услугу на основе переданных данных")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Услуга успешно создан"),
            @ApiResponse(responseCode = "404", description = "Связанные сущности не найдены")
    })
    @PostMapping("/create")
    @AspectAnnotation
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
     * Обновить информацию о существующем услуге.
     *
     * @param serviceDTO DTO с обновленной информацией об услуге.
     * @return HTTP статус OK, если услуга успешно обновлен.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @Operation(summary = "Обновить информацию об услуге", description = "Обновляет существующий услугу")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Услуга успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Услуге или связанные сущности не найдены")
    })
    @PutMapping("/update")
    @AspectAnnotation
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
     * Удалить услугу по ID.
     *
     * @param ID Идентификатор услуги для удаления.
     * @return HTTP статус OK, если услуга успешно удален.
     * @throws ResourceNotFoundException если услуга с данным ID не найден.
     */
    @Operation(summary = "Удалить сервис", description = "Удаляет сервис по его ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сервис успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Сервис не найден")
    })
    @DeleteMapping("/delete/{id}")
    @AspectAnnotation
    public HttpStatus deleteService(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        serviceService.delete(ID);
        return HttpStatus.OK;
    }
}
