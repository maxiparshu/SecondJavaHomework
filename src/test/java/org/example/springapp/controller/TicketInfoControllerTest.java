package org.example.springapp.controller;

import org.example.springapp.dto.TicketInfoDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.service.impl.TicketInfoService;
import org.example.springapp.utils.mapper.TicketInfoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketInfoControllerTest {
    @Mock
    private TicketInfoService ticketInfoService;
    @Mock
    private AttractionService attractionService;
    @Mock
    private TicketInfoMapper ticketInfoMapper;

    @InjectMocks
    private TicketInfoController ticketController;
    @Test
    void readAllSuccess() {
        var ticket1 = TicketInfo.builder().currency("USD").build();
        var ticket2 = TicketInfo.builder().currency("EUR").build();
        var expectedAddresses = List.of(ticket1, ticket2);

        when(ticketInfoService.read()).thenReturn(expectedAddresses);

        var result = ticketController.readAll();

        assertEquals(expectedAddresses, result);

        verify(ticketInfoService, times(1)).read();
    }

    @Test
    void getTicketByIdSuccess() throws ResourceNotFoundException {
        var exceptedTicket = TicketInfo.builder().currency("EUR").build();
        var id = 228L;
        when(ticketInfoService.getByID(id)).thenReturn(exceptedTicket);

        var result = ticketController.getTicketInfoById(id);

        assertEquals(exceptedTicket, result.getBody());
    }

    @Test
    void getTicketByIdNotFound() throws ResourceNotFoundException {
        var id = 228L;
        when(ticketInfoService.getByID(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> ticketController.getTicketInfoById(id));
    }

    @Test
    void createTicketInfoSuccess() throws ResourceNotFoundException {
        var dto = TicketInfoDTO.builder().currency("EUR").attractionID(1L).build();
        var attr = Attraction.builder().id(1L).build();
        var exceptedTicket = TicketInfo.builder().currency("EUR").attraction(attr).build();
        when(ticketInfoMapper.toEntity(dto)).thenReturn(exceptedTicket);
        when(attractionService.getByID(dto.getAttractionID())).thenReturn(attr);
        var status = ticketController.createTicketInfo(dto);
        verify(ticketInfoService, times(1)).create(exceptedTicket);
        assertEquals(HttpStatus.CREATED, status);
    }
    @Test
    void createTicketInfoFailed() throws ResourceNotFoundException {
        var dto = TicketInfoDTO.builder().currency("EUR").attractionID(1L).build();
        var exceptedTicket = TicketInfo.builder().currency("EUR").build();
        when(ticketInfoMapper.toEntity(dto)).thenReturn(exceptedTicket);
        when(attractionService.getByID(dto.getAttractionID())).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> ticketController.createTicketInfo(dto));
    }


    @Test
    void updateTicketInfoSuccess() throws ResourceNotFoundException {
        var dto = TicketInfoDTO.builder().id(1L).currency("EUR").attractionID(1L).build();
        var attr = Attraction.builder().id(1L).build();
        var exceptedTicket = TicketInfo.builder().currency("EUR").attraction(attr).build();
        when(ticketInfoMapper.toEntity(dto)).thenReturn(exceptedTicket);
        when(attractionService.getByID(dto.getAttractionID())).thenReturn(attr);
        var status = ticketController.updateTicketInfo(dto);
        verify(ticketInfoService, times(1)).update(exceptedTicket);
        assertEquals(HttpStatus.OK, status);
    }
    @Test
    void updateTicketInfoFailed() throws ResourceNotFoundException {
        var dto = TicketInfoDTO.builder().id(1L).currency("EUR").attractionID(1L).build();
        var attr = Attraction.builder().id(1L).build();
        var exceptedTicket = TicketInfo.builder().currency("EUR").attraction(attr).build();
        when(ticketInfoMapper.toEntity(dto)).thenReturn(exceptedTicket);
        when(attractionService.getByID(dto.getAttractionID())).thenReturn(attr);
        doThrow(ResourceNotFoundException.class)
                .when(ticketInfoService).update(exceptedTicket);
        assertThrows(ResourceNotFoundException.class, () -> ticketController.updateTicketInfo(dto));
    }
    @Test
    void deleteTicketInfoSuccess() throws ResourceNotFoundException {
        var id = 1L;
        var status = ticketController.deleteTicketInfo(id);
        verify(ticketInfoService, times(1)).delete(id);
        assertEquals(status, HttpStatus.OK);
    }
    @Test
    void deleteTicketInfoFailed() throws ResourceNotFoundException {
        var id = 1L;
        doThrow(ResourceNotFoundException.class)
                .when(ticketInfoService).delete(id);
        assertThrows(ResourceNotFoundException.class, () -> ticketController.deleteTicketInfo(id));
    }
}