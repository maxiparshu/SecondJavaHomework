package org.example.springapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.dto.AttractionDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.service.impl.AddressService;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.service.impl.ServiceService;
import org.example.springapp.service.impl.TicketInfoService;
import org.example.springapp.utils.enums.ServiceType;
import org.example.springapp.utils.mapper.AttractionMapper;
import org.example.springapp.utils.mapper.EntityByIDMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для управления достопримечательностями.
 * Обрабатывает запросы для создания, обновления, удаления и получения информации о достопримечательностях.
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/attractions")
public class AttractionController {

    private final AttractionService attractionService;
    private final AttractionMapper attractionMapper;
    private final AddressService addressService;
    private final TicketInfoService ticketInfoService;
    private final ServiceService serviceService;

    /**
     * Инициализация сущности достопримечательности на основе данных DTO.
     *
     * @param attractionDTO DTO с информацией о достопримечательности.
     * @return Сущность достопримечательности, готовая к сохранению или обновлению.
     * @throws ResourceNotFoundException если не удается найти связанные сущности.
     */
    private Attraction initEntity(final AttractionDTO attractionDTO) throws ResourceNotFoundException {
        var entity = attractionMapper.toEntity(attractionDTO);
        entity.setAddress(addressService.getByID(attractionDTO.getAddressID()));
        if (attractionDTO.getTicketInfoID() != null)
            entity.setTicketInfo(ticketInfoService.getByID(attractionDTO.getTicketInfoID()));
        if (attractionDTO.getServicesID() != null)
            entity.setServices(EntityByIDMapper.fetchByIds(attractionDTO.getServicesID(), serviceService, "Service"));
        else
            entity.setServices(new ArrayList<>());
        attractionService.update(entity);
        return entity;
    }

    /**
     * Получить все достопримечательности.
     *
     * @return Список всех достопримечательностей.
     */
    @GetMapping("/all")
    public List<Attraction> readAll() {
        return attractionService.read();
    }

    /**
     * Получить достопримечательность по ее ID.
     *
     * @param ID Идентификатор достопримечательности.
     * @return ResponseEntity с найденной достопримечательностью.
     * @throws ResourceNotFoundException если достопримечательность с данным ID не найдена.
     */
    @GetMapping("/find/{id}")
    public ResponseEntity<Attraction> getById(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(attractionService.getByID(ID), HttpStatus.OK);
    }

    /**
     * Получить список достопримечательностей по имени.
     *
     * @param name Часть или полное имя достопримечательности.
     * @return Список достопримечательностей, соответствующих имени.
     */
    @GetMapping("/find/")
    public ResponseEntity<List<Attraction>> getByName(final @RequestParam(name = "name") String name) {
        return new ResponseEntity<>(attractionService.findByNameContaining(name), HttpStatus.OK);
    }

    /**
     * Создать новую достопримечательность.
     *
     * @param attractionDTO DTO с данными для создания достопримечательности.
     * @return HTTP статус CREATED, если достопримечательность успешно создана.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @PostMapping("/create")
    public HttpStatus createAttraction(@Valid @RequestBody AttractionDTO attractionDTO) throws ResourceNotFoundException {
        attractionService.create(initEntity(attractionDTO));
        return HttpStatus.CREATED;
    }

    /**
     * Обновить информацию о существующей достопримечательности.
     *
     * @param attractionDTO DTO с обновленной информацией о достопримечательности.
     * @return HTTP статус OK, если достопримечательность успешно обновлена.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @PutMapping("/update")
    public HttpStatus updateAttraction(@Valid @RequestBody AttractionDTO attractionDTO) throws ResourceNotFoundException {
        attractionService.update(initEntity(attractionDTO));
        return HttpStatus.OK;
    }

    /**
     * Удалить достопримечательность по ID.
     *
     * @param ID Идентификатор достопримечательности для удаления.
     * @return HTTP статус OK, если достопримечательность успешно удалена.
     * @throws ResourceNotFoundException если достопримечательность с данным ID не найдена.
     */
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteAttraction(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        attractionService.delete(ID);
        return HttpStatus.OK;
    }

    /**
     * Найти достопримечательности по городу.
     *
     * @param city Название города.
     * @return Список достопримечательностей, расположенных в указанном городе.
     */
    @GetMapping("/search-by-city/{city}")
    public List<Attraction> searchAttractionsByCity(final @PathVariable(name = "city") String city) {
        return attractionService.searchAttractionsByCity(city);
    }

    /**
     * Найти достопримечательности по региону.
     *
     * @param region Название региона.
     * @return Список достопримечательностей, расположенных в указанном регионе.
     */
    @GetMapping("/search-by-region/{region}")
    public List<Attraction> searchAttractionsByRegion(@PathVariable(name = "region") String region) {
        return attractionService.searchAttractionsByRegion(region);
    }

    /**
     * Найти достопримечательности по типу услуги.
     *
     * @param serviceName Название или тип услуги.
     * @return Список достопримечательностей, которые предлагают указанную услугу.
     */
    @GetMapping("/search-by-service-type/{type}")
    public List<Attraction> searchAttractionsByService(@PathVariable(name = "type") String serviceName) {
        return attractionService.searchAttractionsByService(ServiceType.fromDisplayName(serviceName));
    }
}
