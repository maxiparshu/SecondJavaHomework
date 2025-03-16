package org.example.springapp.service.impl;

import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.repository.TicketInfoRepository;
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
class TicketInfoServiceTest {
    @Mock
    private TicketInfoRepository repository;
    @InjectMocks
    private TicketInfoService service;

    private TicketInfo ticket;

    @BeforeEach
    void setUp() {
        ticket = TicketInfo.builder()
                .id(1L)
                .currency("Test")
                .build();
    }

    @Test
    void createAttractionSuccess() {
        when(repository.save(ticket)).thenReturn(ticket);
        var created = service.create(ticket);

        assertEquals(created, ticket);
        verify(repository, times(1)).save(ticket);
    }

    @Test
    void readAllAddressesSuccess() {
        var excepted = List.of(ticket);
        when(repository.findAll()).thenReturn(excepted);
        var attractions = service.read();

        assertEquals(excepted, attractions);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getAddressByIdSuccess() throws ResourceNotFoundException {
        when(repository.getTicketInfoById(1L)).thenReturn(Optional.of(ticket));
        var actual = service.getByID(1L);

        assertEquals(actual, ticket);
        verify(repository, times(1)).getTicketInfoById(1L);
    }

    @Test
    void getAddressByIdThrowsException() {
        when(repository.getTicketInfoById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getByID(1L));
    }

    @Test
    void updateAddressSuccess() throws ResourceNotFoundException {
        when(repository.existsById(1L)).thenReturn(true);
        service.update(ticket);
        verify(repository, times(1)).save(ticket);
    }

    @Test
    void updateAddressThrowsException() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.update(ticket));
        verify(repository, never()).save(ticket);
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