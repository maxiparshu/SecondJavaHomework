package org.example.springapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.springapp.dto.AddressDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.service.impl.AddressService;
import org.example.springapp.utils.mapper.AddressMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/addresses")
public class AddressController {
    private final AddressService addressService;
    private final AddressMapper addressMapper;
    @GetMapping("/all")
    public List<Address> readAll() {
        return addressService.read();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Address> getAddressById(final @PathVariable(name = "id")
                                              Long ID) throws ResourceNotFoundException {
        return new ResponseEntity<>(addressService.getByID(ID), HttpStatus.OK);

    }

    @PostMapping("/create")
    public HttpStatus createAddress(@Valid @RequestBody AddressDTO AddressDTO) {
        var entity = addressMapper.toEntity(AddressDTO);
        addressService.create(entity);
        return HttpStatus.CREATED;
    }

    @PutMapping("/update")
    public HttpStatus updateAddress(@Valid @RequestBody AddressDTO AddressDTO) throws ResourceNotFoundException {
        var entity = addressMapper.toEntity(AddressDTO);
        addressService.update(entity);
        return HttpStatus.OK;
    }
    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteAddress(final @PathVariable(name = "id")
                                       Long ID) throws ResourceNotFoundException {
        addressService.delete(ID);
        return HttpStatus.OK;
    }
}
