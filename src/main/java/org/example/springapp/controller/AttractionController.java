package org.example.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Attraction Controller", description = "Управление достопримечательностями")
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
        return entity;
    }

    /**
     * Получить все достопримечательности.
     *
     * @return Список всех достопримечательностей.
     */
    @Operation(summary = "Получить все достопримечательности",
            description = "Возвращает список всех доступных достопримечательностей")
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
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
    @Operation(summary = "Получить достопримечательность по ID",
            description = "Возвращает достопримечательность по указанному ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Достопримечательность найдена"),
            @ApiResponse(responseCode = "404", description = "Достопримечательность не найдена")
    })
    @GetMapping("/find/{id}")
    public ResponseEntity<Attraction> getById(
            @Parameter(description = "Идентификатор достопримечательности", example = "1")
            @PathVariable(name = "id") Long ID
    ) throws ResourceNotFoundException {
        return new ResponseEntity<>(attractionService.getByID(ID), HttpStatus.OK);
    }


    /**
     * Получить список достопримечательностей по имени.
     *
     * @param name Часть или полное имя достопримечательности.
     * @return Список достопримечательностей, соответствующих имени.
     */

    @Operation(summary = "Получить достопримечательности по имени",
            description = "Возвращает список достопримечательностей, название которых содержит заданное значение")
    @ApiResponse(responseCode = "200", description = "Список достопримечательностей успешно получен")
    @GetMapping("/find/")
    public ResponseEntity<List<Attraction>> getByName(
            @Parameter(description = "Часть или полное имя достопримечательности", example = "Tower")
            @RequestParam(name = "name") String name
    ) {
        return new ResponseEntity<>(attractionService.findByNameContaining(name), HttpStatus.OK);
    }

    /**
     * Создать новую достопримечательность.
     *
     * @param attractionDTO DTO с данными для создания достопримечательности.
     * @return HTTP статус CREATED, если достопримечательность успешно создана.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @Operation(summary = "Создать новую достопримечательность",
            description = "Создаёт новую достопримечательность на основе данных из DTO")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Достопримечательность успешно создана"),
            @ApiResponse(responseCode = "404", description = "Связанные сущности не найдены")
    })
    @PostMapping("/create")
    public HttpStatus createAttraction(
            @Parameter(description = "Данные для создания новой достопримечательности")
            @Valid @RequestBody AttractionDTO attractionDTO
    ) throws ResourceNotFoundException {
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
    @Operation(summary = "Обновить информацию о достопримечательности",
            description = "Обновляет данные существующей достопримечательности")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Достопримечательность успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Достопримечательность или связанные сущности не найдены")
    })
    @PutMapping("/update")
    public HttpStatus updateAttraction(
            @Parameter(description = "Обновлённые данные достопримечательности")
            @Valid @RequestBody AttractionDTO attractionDTO
    ) throws ResourceNotFoundException {
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
    @Operation(summary = "Удалить достопримечательность",
            description = "Удаляет достопримечательность по её ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Достопримечательность успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Достопримечательность не найдена")
    })
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteAttraction(
            @Parameter(description = "Идентификатор удаляемой достопримечательности", example = "1")
            @PathVariable(name = "id") Long ID
    ) throws ResourceNotFoundException {
        attractionService.delete(ID);
        return HttpStatus.OK;
    }

    /**
     * Найти достопримечательности по городу.
     *
     * @param city Название города.
     * @return Список достопримечательностей, расположенных в указанном городе.
     */
    @Operation(summary = "Поиск достопримечательностей по городу")
    @ApiResponse(responseCode = "200", description = "Список достопримечательностей найден")
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
    @Operation(summary = "Поиск достопримечательностей по региону")
    @ApiResponse(responseCode = "200", description = "Список достопримечательностей найден")
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
    @Operation(summary = "Поиск достопримечательностей по типу услуги")
    @ApiResponse(responseCode = "200", description = "Список достопримечательностей найден")
    @GetMapping("/search-by-service-type/{type}")
    public List<Attraction> searchAttractionsByService(@PathVariable(name = "type") String serviceName) {
        return attractionService.searchAttractionsByService(ServiceType.fromDisplayName(serviceName));
    }
}
