package org.example.springapp.service.impl;

import lombok.AllArgsConstructor;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.repository.TicketInfoRepository;
import org.example.springapp.service.CRUDService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketInfoService implements CRUDService<TicketInfo> {
    private final TicketInfoRepository repository;

    @Override
    public TicketInfo create(TicketInfo entity) {
        return repository.save(entity);
    }

    @Override
    public List<TicketInfo> read() {
        return repository.findAll();
    }

    @Override
    public TicketInfo getByID(Long id) throws ResourceNotFoundException {
        return repository.getTicketInfoById(id).orElseThrow(()
                -> new ResourceNotFoundException(("Ticket info with this id doesn't exists")));
    }

    @Override
    public void update(TicketInfo entity) throws ResourceNotFoundException {
        if (!repository.existsById(entity.getId())) {
            throw new ResourceNotFoundException("Ticket info with this id doesn't exists");
        }
        repository.save(entity);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket info with this id doesn't exists");
        }
        repository.deleteById(id);
    }
}
