package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.repository.AddressRepository;
import org.example.springapp.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService implements CRUDService<Address> {
    private final AddressRepository repository;

    @Override
    public Address create(Address entity) {
        return repository.save(entity);
    }

    @Override
    public List<Address> read() {
        return repository.findAll();
    }

    @Override
    public Address getByID(Long id) throws ResourceNotFoundException {
        return repository.getAddressById(id).orElseThrow(() -> new ResourceNotFoundException("Address with this id doesn't exists"));
    }

    @Override
    public void update(Address entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Address with this id doesn't exists");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Address with this id doesn't exists");
        }
        repository.deleteById(id);
    }
}
