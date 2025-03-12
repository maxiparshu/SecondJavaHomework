package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.repository.AttractionRepository;
import org.example.springapp.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AttractionService implements CRUDService<Attraction> {
    private final AttractionRepository repository;

    @Override
    public Attraction create(Attraction entity) {
        return repository.save(entity);
    }

    @Override
    public List<Attraction> read() {
        return repository.findAll();
    }

    @Override
    public Attraction getByID(Long id) throws ResourceNotFoundException {
        return repository.getAttractionById(id).orElseThrow(() -> new ResourceNotFoundException("Attraction with this id doesn't exists"));
    }

    @Override
    public void update(Attraction entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Attraction with this id doesn't exists");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Attraction with this id doesn't exists");
        }
        repository.deleteById(id);
    }
}
