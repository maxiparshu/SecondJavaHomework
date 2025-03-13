package org.example.springapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.dto.AttractionDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.utils.mapper.AttractionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/attractions")
public class AttractionController {
    private final AttractionService attractionService;
    private final AttractionMapper attractionMapper;

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
    public HttpStatus createAttraction(@Valid @RequestBody AttractionDTO attractionDTO) {
        var entity = attractionMapper.toEntity(attractionDTO);
        attractionService.create(entity);
        return HttpStatus.CREATED;
    }

}
