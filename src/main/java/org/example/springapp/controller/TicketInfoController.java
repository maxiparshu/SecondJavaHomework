package org.example.springapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.dto.TicketInfoDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.service.impl.TicketInfoService;
import org.example.springapp.utils.mapper.TicketInfoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления информацией о билетах.
 * Обрабатывает запросы для создания, обновления, удаления и получения информации о билетах.
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/tickets")
@Tag(name = "TicketInfo Controller", description = "Управление билетами")
public class TicketInfoController {

    private final TicketInfoService ticketInfoService;
    private final AttractionService attractionService;
    private final TicketInfoMapper ticketInfoMapper;

    /**
     * Получить всю информацию о билетах.
     *
     * @return Список всех билетов.
     */
    @Operation(summary = "Получить весь список билетов", description = "Возвращает список всех доступных билетов")
    @ApiResponse(responseCode = "200", description = "Список билетов успешно получен")
    @GetMapping("/all")
    public List<TicketInfo> readAll() {
        return ticketInfoService.read();
    }

    /**
     * Получить информацию о билете по его ID.
     *
     * @param ID Идентификатор билета.
     * @return ResponseEntity с найденной информацией о билете.
     * @throws ResourceNotFoundException если билет с данным ID не найден.
     */
    @Operation(summary = "Получить информацию о билете по ID", description = "Возвращает информацию о билете по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о билете найдена"),
            @ApiResponse(responseCode = "404", description = "Билет с таким ID не найден")
    })
    @GetMapping("find/{id}")
    public ResponseEntity<TicketInfo> getTicketInfoById(
            @Parameter(description = "Идентификатор билета", example = "1")
            @PathVariable(name = "id") Long ID
    ) throws ResourceNotFoundException {
        return new ResponseEntity<>(ticketInfoService.getByID(ID), HttpStatus.OK);
    }

    /**
     * Создать новую информацию о билете.
     *
     * @param ticketInfoDTO DTO с данными для создания информации о билете.
     * @return HTTP статус CREATED, если информация о билете успешно создана.
     * @throws ResourceNotFoundException если связанные сущности (например, достопримечательность) не найдены.
     */
    @Operation(summary = "Создать новую информацию о билете", description = "Создает новую запись о билете для выбранной достопримечательности")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Информация о билете успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Связанная достопримечательность не найдена")
    })
    @PostMapping("/create")
    public HttpStatus createTicketInfo(
            @Parameter(description = "DTO с данными для создания информации о билете")
            @Valid @RequestBody TicketInfoDTO ticketInfoDTO
    ) throws ResourceNotFoundException {
        var entity = ticketInfoMapper.toEntity(ticketInfoDTO);
        entity.setAttraction(attractionService.getByID(ticketInfoDTO.getAttractionID()));
        ticketInfoService.create(entity);
        return HttpStatus.CREATED;
    }

    /**
     * Обновить информацию о существующем билете.
     *
     * @param ticketInfoDTO DTO с обновленной информацией о билете.
     * @return HTTP статус OK, если информация о билете успешно обновлена.
     * @throws ResourceNotFoundException если связанные сущности (например, достопримечательность) не найдены.
     */
    @Operation(summary = "Обновить информацию о билете", description = "Обновляет существующую информацию о билете")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о билете успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Билет или связанная достопримечательность не найдены")
    })
    @PutMapping("/update")
    public HttpStatus updateTicketInfo(
            @Parameter(description = "DTO с обновленной информацией о билете")
            @Valid @RequestBody TicketInfoDTO ticketInfoDTO
    ) throws ResourceNotFoundException {
        var entity = ticketInfoMapper.toEntity(ticketInfoDTO);
        entity.setAttraction(attractionService.getByID(ticketInfoDTO.getAttractionID()));
        ticketInfoService.update(entity);
        return HttpStatus.OK;
    }

    /**
     * Удалить информацию о билете по ID.
     *
     * @param ID Идентификатор информации о билете для удаления.
     * @return HTTP статус OK, если информация о билете успешно удалена.
     * @throws ResourceNotFoundException если информация о билете с данным ID не найдена.
     */
    @Operation(summary = "Удалить информацию о билете", description = "Удаляет информацию о билете по его ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о билете успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Билет с таким ID не найден")
    })
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteTicketInfo(
            @Parameter(description = "Идентификатор информации о билете для удаления", example = "1")
            @PathVariable(name = "id") Long ID
    ) throws ResourceNotFoundException {
        ticketInfoService.delete(ID);
        return HttpStatus.OK;
    }
}
