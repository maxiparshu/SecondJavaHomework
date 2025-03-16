package org.example.springapp.service.impl;

import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Service;
import org.example.springapp.repository.ServiceRepository;
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
class ServiceServiceTest {
    @Mock
    private ServiceRepository repository;
    @InjectMocks
    private ServiceService service;

    private Service serviceData;

    @BeforeEach
    void setUp() {
        serviceData = Service.builder()
                .id(1L)
                .name("Test")
                .build();
    }

    @Test
    void createAttractionSuccess() {
        when(repository.save(serviceData)).thenReturn(serviceData);
        var created = service.create(serviceData);

        assertEquals(created, serviceData);
        verify(repository, times(1)).save(serviceData);
    }

    @Test
    void readAllAddressesSuccess() {
        var excepted = List.of(serviceData);
        when(repository.findAll()).thenReturn(excepted);
        var attractions = service.read();

        assertEquals(excepted, attractions);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAddressByIdSuccess() throws ResourceNotFoundException {
        when(repository.getServiceById(1L)).thenReturn(Optional.of(serviceData));
        var actual = service.getByID(1L);

        assertEquals(actual, serviceData);
        verify(repository, times(1)).getServiceById(1L);
    }

    @Test
    void getAddressByIdThrowsException() {
        when(repository.getServiceById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getByID(1L));
    }

    @Test
    void updateAddressSuccess() throws ResourceNotFoundException {
        when(repository.existsById(1L)).thenReturn(true);
        service.update(serviceData);
        verify(repository, times(1)).save(serviceData);
    }

    @Test
    void updateAddressThrowsException() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.update(serviceData));
        verify(repository, never()).save(serviceData);
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