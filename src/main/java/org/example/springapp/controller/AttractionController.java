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
import org.example.springapp.utils.mapper.AttractionMapper;
import org.example.springapp.utils.mapper.EntityByIDMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/attractions")
public class AttractionController {
    private final AttractionService attractionService;
    private final AttractionMapper attractionMapper;
    private final AddressService addressService;
    private final TicketInfoService ticketInfoService;
    private final ServiceService serviceService;

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

    @GetMapping("/all")
    public List<Attraction> readAll() {
        return attractionService.read();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Attraction> getById(final @PathVariable(name = "id")
                                              Long ID) throws ResourceNotFoundException {
        return new ResponseEntity<>(attractionService.getByID(ID), HttpStatus.OK);

    }

    @PostMapping("/create")
    public HttpStatus createAttraction(@Valid @RequestBody AttractionDTO attractionDTO) throws ResourceNotFoundException {
        attractionService.create(initEntity(attractionDTO));
        return HttpStatus.CREATED;
    }

    @PutMapping("/update")
    public HttpStatus updateAttraction(@Valid @RequestBody AttractionDTO attractionDTO) throws ResourceNotFoundException {
        attractionService.update(initEntity(attractionDTO));
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteAttraction(final @PathVariable(name = "id")
                                       Long ID) throws ResourceNotFoundException {
        attractionService.delete(ID);
        return HttpStatus.OK;
    }
}
