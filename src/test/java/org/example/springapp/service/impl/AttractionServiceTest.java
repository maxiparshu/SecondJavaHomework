package org.example.springapp.service.impl;

import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.repository.AttractionRepository;
import org.example.springapp.utils.enums.ServiceType;
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
class AttractionServiceTest {
    @Mock
    private AttractionRepository repository;
    @InjectMocks
    private AttractionService service;

    private Attraction attraction;

    @BeforeEach
    void setUp() {
        attraction = Attraction.builder()
                .id(1L)
                .name("Test")
                .build();
    }

    @Test
    void createAttractionSuccess() {
        when(repository.save(attraction)).thenReturn(attraction);
        var created = service.create(attraction);

        assertEquals(created, attraction);
        verify(repository, times(1)).save(attraction);
    }

    @Test
    void readAllAddressesSuccess() {
        var excepted = List.of(attraction);
        when(repository.findAll()).thenReturn(excepted);
        var attractions = service.read();

        assertEquals(excepted, attractions);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAddressByIdSuccess() throws ResourceNotFoundException {
        when(repository.getAttractionById(1L)).thenReturn(Optional.of(attraction));
        var actual = service.getByID(1L);

        assertEquals(actual, attraction);
        verify(repository, times(1)).getAttractionById(1L);
    }

    @Test
    void getAddressByIdThrowsException() {
        when(repository.getAttractionById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getByID(1L));
    }

    @Test
    void updateAddressSuccess() throws ResourceNotFoundException {
        when(repository.existsById(1L)).thenReturn(true);
        service.update(attraction);
        verify(repository, times(1)).save(attraction);
    }

    @Test
    void updateAddressThrowsException() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.update(attraction));
        verify(repository, never()).save(attraction);
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
    @Test
    void findByNameSuccess(){
        var name = "Tes";
        var lists = List.of(attraction);
        when(repository.findByNameContainingIgnoreCase(name)).thenReturn(lists);
        var actual = service.findByNameContaining(name);

        assertEquals(actual, lists);
    }
    @Test
    void searchByCitySuccess(){
        var city = "Tes";
        var lists = List.of(attraction);
        when(repository.findByAddressCity(city)).thenReturn(lists);
        var actual = service.searchAttractionsByCity(city);

        assertEquals(actual, lists);
    }
    @Test
    void searchByRegionSuccess(){
        var region = "Tes";
        var lists = List.of(attraction);
        when(repository.findByAddressRegion(region)).thenReturn(lists);
        var actual = service.searchAttractionsByRegion(region);

        assertEquals(actual, lists);
    }
    @Test
    void searchByServiceSuccess(){
        var type = ServiceType.GUIDE;
        var lists = List.of(attraction);
        when(repository.findByServicesServiceType(type)).thenReturn(lists);
        var actual = service.searchAttractionsByService(type);

        assertEquals(actual, lists);
    }
}
    

