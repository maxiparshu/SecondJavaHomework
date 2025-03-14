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

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/services")
public class ServiceController {
    private final ServiceService serviceService;
    private final ServiceMapper serviceMapper;
    private final AttractionService attractionService;


    @GetMapping("/all")
    public List<Service> readAll() {
        return serviceService.read();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Service> getServiceById(final @PathVariable(name = "id")
                                                  Long ID) throws ResourceNotFoundException {
        return new ResponseEntity<>(serviceService.getByID(ID), HttpStatus.OK);

    }

    @PostMapping("/create")
    public HttpStatus createService(@Valid @RequestBody ServiceDTO serviceDTO) throws ResourceNotFoundException {
        var entity = serviceMapper.toEntity(serviceDTO);
        entity.setAttractions(EntityByIDMapper.fetchByIds(serviceDTO.getAttractionsID(), attractionService, "Attraction"));
        serviceService.create(entity);
        return HttpStatus.CREATED;
    }

    @PutMapping("/update")
    public HttpStatus updateService(@Valid @RequestBody ServiceDTO serviceDTO) throws ResourceNotFoundException {
        var entity = serviceMapper.toEntity(serviceDTO);
        entity.setAttractions(EntityByIDMapper.fetchByIds(serviceDTO.getAttractionsID(), attractionService, "Attraction"));
        entity.getAttractions().stream()
                .peek(x -> System.out.println(x.getName()))
                .findAny().ifPresent(System.out::println);
        serviceService.update(entity);
        return HttpStatus.OK;
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteService(final @PathVariable(name = "id")
                                    Long ID) throws ResourceNotFoundException {
        serviceService.delete(ID);
        return HttpStatus.OK;
    }
}
