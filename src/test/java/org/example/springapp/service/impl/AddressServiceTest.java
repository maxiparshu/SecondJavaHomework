package org.example.springapp.service.impl;

import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
    @Mock
    private AddressRepository repository;
    @InjectMocks
    private AddressService service;

    private Address address;

    @BeforeEach
    void setUp() {
        address = Address.builder()
                .id(1L)
                .city("Test")
                .build();
    }

    @Test
    void createAddressSuccess() {
        when(repository.save(address)).thenReturn(address);
        Address created = service.create(address);

        assertEquals(created, address);
        verify(repository, times(1)).save(address);
    }

    @Test
    void readAllAddressesSuccess() {
        var excepted = List.of(address);
        when(repository.findAll()).thenReturn(excepted);
        var addresses = service.read();

        assertEquals(excepted, addresses);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAddressByIdSuccess() throws ResourceNotFoundException {
        when(repository.getAddressById(1L)).thenReturn(Optional.of(address));
        var actual = service.getByID(1L);

        assertEquals(actual, address);
        verify(repository, times(1)).getAddressById(1L);
    }

    @Test
    void getAddressByIdThrowsException() {
        when(repository.getAddressById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getByID(1L));
    }

    @Test
    void updateAddressSuccess() throws ResourceNotFoundException {
        when(repository.existsById(1L)).thenReturn(true);
        service.update(address);
        verify(repository, times(1)).save(address);
    }

    @Test
    void updateAddressThrowsException() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.update(address));
        verify(repository, never()).save(address);
    }

    @Test
    void deleteAddressSuccess() {
        when(repository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAddressThrowsException() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
        verify(repository, never()).deleteById(1L);
    }
}