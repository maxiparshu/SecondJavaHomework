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

@RestController
@AllArgsConstructor
@RequestMapping("api/tickets")
public class TicketInfoController {
    private final TicketInfoService ticketInfoService;
    private final AttractionService attractionService;
    private final TicketInfoMapper ticketInfoMapper;
    @GetMapping("/all")
    public List<TicketInfo> readAll() {
        return ticketInfoService.read();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<TicketInfo> getTicketInfoById(final @PathVariable(name = "id")
                                                  Long ID) throws ResourceNotFoundException {
        return new ResponseEntity<>(ticketInfoService.getByID(ID), HttpStatus.OK);

    }

    @PostMapping("/create")
    public HttpStatus createTicketInfo(@Valid @RequestBody TicketInfoDTO ticketInfoDTO) throws ResourceNotFoundException {
        var entity = ticketInfoMapper.toEntity(ticketInfoDTO);
        entity.setAttraction(attractionService.getByID(ticketInfoDTO.getAttractionID()));
        ticketInfoService.create(entity);
        return HttpStatus.CREATED;
    }

    @PutMapping("/update")
    public HttpStatus updateTicketInfo(@Valid @RequestBody TicketInfoDTO ticketInfoDTO) throws ResourceNotFoundException {
        var entity = ticketInfoMapper.toEntity(ticketInfoDTO);
        entity.setAttraction(attractionService.getByID(ticketInfoDTO.getAttractionID()));
        ticketInfoService.update(entity);
        return HttpStatus.OK;
    }
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteTicketInfo(final @PathVariable(name = "id")
                                    Long ID) throws ResourceNotFoundException {
        ticketInfoService.delete(ID);
        return HttpStatus.OK;
    }
}
