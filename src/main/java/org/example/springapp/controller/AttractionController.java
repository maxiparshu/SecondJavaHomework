package org.example.springapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.dto.AttractionDTO;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.utils.mapper.AttractionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/attractions")
public class AttractionController {
    private final AttractionService attractionService;
    private final AttractionMapper attractionMapper;
    @GetMapping("/check")
    public String check(){
        return "Hello world!!!";
    }
    @PostMapping("/create")
    public HttpStatus createAttraction(@Valid @RequestBody AttractionDTO attractionDTO) {
        var entity = attractionMapper.toEntity(attractionDTO);
        return HttpStatus.CREATED;
    }
}
