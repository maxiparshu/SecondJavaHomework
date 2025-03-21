package org.example.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.aspect.AspectAnnotation;
import org.example.springapp.dto.AddressDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.service.impl.AddressService;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.utils.mapper.AddressMapper;
import org.example.springapp.utils.mapper.EntityByIDMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для управления адресами.
 * Обрабатывает запросы для создания, обновления, удаления и получения информации об адресах.
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/addresses")
@Tag(name = "Address Controller", description = "Управление адресами")
public class AddressController {

    private final AddressService addressService;
    private final AttractionService attractionService;
    private final AddressMapper addressMapper;

    /**
     * Получить все адреса.
     *
     * @return Список всех адресов.
     */
    @GetMapping("/all")
    @Operation(summary = "Получить все адреса", description = "Возвращает список всех адресов.")
    @ApiResponse(responseCode = "200", description = "Успешное получение списка адресов")
    public List<Address> readAll() {
        return addressService.read();
    }

    /**
     * Получить адрес по его ID.
     *
     * @param ID Идентификатор адреса.
     * @return ResponseEntity с адресом, если найден.
     * @throws ResourceNotFoundException если адрес с данным ID не найден.
     */
    @Operation(summary = "Получить адрес по ID", description = "Возвращает адрес по его ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Адрес найден",
                    content = @Content(schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "404", description = "Адрес не найден")
    })
    @GetMapping("find/{id}")
    @AspectAnnotation
    public ResponseEntity<Address> getAddressById(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(addressService.getByID(ID), HttpStatus.OK);
    }

    /**
     * Создать новый адрес.
     *
     * @param addressDTO DTP с информацией для создания нового адреса.
     * @return HTTP статус CREATED, если адрес был успешно создан.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */

    @PostMapping("/create")
    @Operation(summary = "Создать новый адрес", description = "Создает новый адрес на основе данных из DTO.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Адрес успешно создан"),
            @ApiResponse(responseCode = "404", description = "Связанные сущности не найдены",
                    content = @Content(mediaType = "application/json"))
    })
    @AspectAnnotation
    public HttpStatus createAddress(@Valid @RequestBody AddressDTO addressDTO) throws ResourceNotFoundException {
        var entity = addressMapper.toEntity(addressDTO);
        if (addressDTO.getAttractionID() != null)
            entity.setAttraction(EntityByIDMapper.fetchByIds(addressDTO.getAttractionID(), attractionService, "Attraction"));
        else
            entity.setAttraction(new ArrayList<>());
        addressService.create(entity);
        return HttpStatus.CREATED;
    }

    /**
     * Обновить информацию об адресе.
     *
     * @param addressDTO DTO с обновленной информацией об адресе.
     * @return HTTP статус OK, если адрес был успешно обновлен.
     * @throws ResourceNotFoundException если связанные сущности не найдены.
     */
    @PutMapping("/update")
    @Operation(summary = "Обновить адрес", description = "Обновляет существующий адрес на основе данных из DTO.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Адрес успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден или связанные сущности не найдены",
                    content = @Content(mediaType = "application/json"))
    })
    @AspectAnnotation
    public HttpStatus updateAddress(@Valid @RequestBody AddressDTO addressDTO) throws ResourceNotFoundException {
        var entity = addressMapper.toEntity(addressDTO);
        if (addressDTO.getAttractionID() != null)
            entity.setAttraction(EntityByIDMapper.fetchByIds(addressDTO.getAttractionID(), attractionService, "Attraction"));
        else
            entity.setAttraction(new ArrayList<>());
        System.out.println(entity.getAttraction().size());
        addressService.update(entity);
        return HttpStatus.OK;
    }

    /**
     * Удалить адрес по его ID.
     *
     * @param ID Идентификатор адреса для удаления.
     * @return HTTP статус OK, если адрес был успешно удален.
     * @throws ResourceNotFoundException если адрес с данным ID не найден.
     */
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить адрес", description = "Удаляет адрес по его ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Адрес успешно удален"),
            @ApiResponse(responseCode = "404", description = "Адрес не найден")
    })
    @AspectAnnotation
    public HttpStatus deleteAddress(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        addressService.delete(ID);
        return HttpStatus.OK;
    }
}