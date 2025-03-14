package org.example.springapp.controller;

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
public class TicketInfoController {

    private final TicketInfoService ticketInfoService;
    private final AttractionService attractionService;
    private final TicketInfoMapper ticketInfoMapper;

    /**
     * Получить все информацию о билетах.
     *
     * @return Список всех билетов.
     */
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
    @GetMapping("find/{id}")
    public ResponseEntity<TicketInfo> getTicketInfoById(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(ticketInfoService.getByID(ID), HttpStatus.OK);
    }

    /**
     * Создать новую информацию о билете.
     *
     * @param ticketInfoDTO DTO с данными для создания информации о билете.
     * @return HTTP статус CREATED, если информация о билете успешно создана.
     * @throws ResourceNotFoundException если связанные сущности (например, достопримечательность) не найдены.
     */
    @PostMapping("/create")
    public HttpStatus createTicketInfo(@Valid @RequestBody TicketInfoDTO ticketInfoDTO) throws ResourceNotFoundException {
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
    @PutMapping("/update")
    public HttpStatus updateTicketInfo(@Valid @RequestBody TicketInfoDTO ticketInfoDTO) throws ResourceNotFoundException {
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
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteTicketInfo(final @PathVariable(name = "id") Long ID)
            throws ResourceNotFoundException {
        ticketInfoService.delete(ID);
        return HttpStatus.OK;
    }
}
