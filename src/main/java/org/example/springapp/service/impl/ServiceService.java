package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Service;
import org.example.springapp.repository.ServiceRepository;
import org.example.springapp.service.CRUDService;

import java.util.List;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class ServiceService implements CRUDService<Service> {
    private final ServiceRepository repository;
    @Override
    public Service create(Service entity) {
        return repository.save(entity);
    }

    @Override
    public List<Service> read() {
        return repository.findAll();
    }

    @Override
    public Service getByID(Long id) throws ResourceNotFoundException {
        return repository.getServiceById(id).orElseThrow(()
                -> new ResourceNotFoundException(("Service with this id doesn't exists")));
    }

    @Override
    public void update(Service entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Service with this id doesn't exists");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Service with this id doesn't exists");
        }
        repository.deleteById(id);
    }
}
