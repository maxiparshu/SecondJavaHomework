package org.example.springapp.controller;

import org.example.springapp.dto.AttractionDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.model.Attraction;
import org.example.springapp.model.TicketInfo;
import org.example.springapp.service.impl.AddressService;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.service.impl.ServiceService;
import org.example.springapp.service.impl.TicketInfoService;
import org.example.springapp.utils.enums.ServiceType;
import org.example.springapp.utils.mapper.AttractionMapper;
import org.example.springapp.utils.mapper.EntityByIDMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.example.springapp.model.Service.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttractionControllerTest {
    @Mock
    private AddressService addressService;
    @Mock
    private ServiceService serviceService;
    @Mock
    private TicketInfoService ticketService;
    @Mock
    private AttractionService attractionService;
    @Mock
    private AttractionMapper attractionMapper;
    @InjectMocks
    private AttractionController attractionController;

    @Test
    void readAllSuccess() {
        var testValue1 = Attraction.builder().name("test1").build();
        var testValue2 = Attraction.builder().name("test2").build();
        var exceptedList = List.of(testValue1, testValue2);
        when(attractionService.read()).thenReturn(exceptedList);

        var receivedList = attractionController.readAll();
        assertEquals(receivedList, exceptedList);
        verify(attractionService, times(1)).read();
    }

    @Test
    void getByIdSuccess() throws ResourceNotFoundException {
        var id = 1L;
        var exceptedAttraction = Attraction.builder().name("test1").build();
        when(attractionService.getByID(id)).thenReturn(exceptedAttraction);
        var response = attractionController.getById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), exceptedAttraction);
    }

    @Test
    void getByIdNotFound() throws ResourceNotFoundException {
        var id = 1L;
        when(attractionService.getByID(id)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> attractionController.getById(id));
    }

    @Test
    void getByNameSuccess() {
        var name = "test";
        var exceptedAddresses = List.of(Attraction.builder().name("test1").build());
        when(attractionService.findByNameContaining(name)).thenReturn(exceptedAddresses);
        var response = attractionController.getByName(name);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), exceptedAddresses);
    }

    @Test
    void shouldCreateAttractionSuccessfully() throws ResourceNotFoundException {
        var dto = AttractionDTO.builder()
                .name("Test Attraction")
                .addressID(1L)
                .ticketInfoID(2L)
                .servicesID(List.of(3L, 4L))
                .build();

        var address = Address.builder().id(1L).city("Test City").build();
        var ticketInfo = TicketInfo.builder().id(2L).price(BigDecimal.valueOf(10)).build();
        var services = List.of(
                builder().id(3L).name("Guide").build(),
                builder().id(4L).name("Audio Tour").build()
        );

        var expectedEntity = Attraction.builder()
                .name("Test Attraction")
                .address(address)
                .ticketInfo(ticketInfo)
                .services(services)
                .build();

        when(attractionMapper.toEntity(dto)).thenReturn(expectedEntity);
        when(addressService.getByID(1L)).thenReturn(address);
        when(ticketService.getByID(2L)).thenReturn(ticketInfo);
        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(dto.getServicesID(), serviceService, "Service")).thenReturn(services);
            var status = attractionController.createAttraction(dto);
            verify(attractionService, times(1)).create(expectedEntity);
            assertEquals(HttpStatus.CREATED, status);
        }
    }


    @Test
    void shouldCreateAttractionWithoutTicketsAndServices() throws ResourceNotFoundException {
        var dto = AttractionDTO.builder()
                .name("Test Attraction")
                .addressID(1L)
                .build();

        var address = Address.builder().id(1L).city("Test City").build();

        var expectedEntity = Attraction.builder()
                .name("Test Attraction")
                .address(address)
                .services(new ArrayList<>())
                .build();

        when(attractionMapper.toEntity(dto)).thenReturn(expectedEntity);
        when(addressService.getByID(1L)).thenReturn(address);

        var status = attractionController.createAttraction(dto);

        verify(attractionService, times(1)).create(expectedEntity);
        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    void shouldThrowExceptionWhenAddressNotFound() throws ResourceNotFoundException {
        var dto = AttractionDTO.builder()
                .name("Test Attraction")
                .addressID(99L)
                .build();

        when(addressService.getByID(99L)).thenThrow(new ResourceNotFoundException("Address not found"));

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> attractionController.createAttraction(dto));

        assertEquals("Address not found", exception.getMessage());
        verify(attractionService, never()).create(any());
    }

    @Test
    void deleteAttractionSuccess() throws ResourceNotFoundException {
        Long addressId = 1L;

        var status = attractionController.deleteAttraction(addressId);

        verify(attractionService, times(1)).delete(addressId);

        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void deleteAttractionInvalidID() throws ResourceNotFoundException {
        Long invalidId = 999L;

        doThrow(new ResourceNotFoundException("Address not found"))
                .when(attractionService).delete(invalidId);

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> attractionController.deleteAttraction(invalidId));

        assertEquals("Address not found", exception.getMessage());

        verify(attractionService, times(1)).delete(invalidId);
    }
    @Test
    void updateAttractionSuccess() throws ResourceNotFoundException {
        var dto = AttractionDTO.builder()
                .id(1L)
                .name("Test Attraction")
                .addressID(1L)
                .build();

        var address = Address.builder().id(1L).city("Test City").build();
        var expectedEntity = Attraction.builder()
                .id(1L)
                .name("Test Attraction")
                .address(address)
                .services(new ArrayList<>())
                .build();

        when(attractionMapper.toEntity(dto)).thenReturn(expectedEntity);
        when(addressService.getByID(1L)).thenReturn(address);

        var status = attractionController.updateAttraction(dto);

        verify(attractionService, times(1)).update(expectedEntity);
        assertEquals(HttpStatus.OK, status);
    }
    @Test
    void updateAttractionFailed() throws ResourceNotFoundException {
        var dto = AttractionDTO.builder()
                .name("Test Attraction")
                .addressID(99L)
                .build();

        when(addressService.getByID(99L)).thenThrow(new ResourceNotFoundException("Address not found"));

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> attractionController.updateAttraction(dto));

        assertEquals("Address not found", exception.getMessage());
        verify(attractionService, never()).update(any());
    }
    @Test
    void searchAttractionsByCity() {
        var city = "test";
        var exceptedAddresses = List.of(Attraction.builder().name("test1").build());
        when(attractionService.searchAttractionsByCity(city)).thenReturn(exceptedAddresses);
        var response = attractionController.searchAttractionsByCity(city);
        assertEquals(response, exceptedAddresses);
    }

    @Test
    void searchAttractionsByRegion() {
        var region = "test";
        var exceptedAddresses = List.of(Attraction.builder().name("test1").build());
        when(attractionService.searchAttractionsByRegion(region)).thenReturn(exceptedAddresses);
        var response = attractionController.searchAttractionsByRegion(region);
        assertEquals(response, exceptedAddresses);
    }

    @Test
    void searchAttractionsByService() {
        var name = ServiceType.CAR_EXCURSION;
        var exceptedAddresses = List.of(Attraction.builder().name("test1").build());
        when(attractionService.searchAttractionsByService(name)).thenReturn(exceptedAddresses);
        var response = attractionController.searchAttractionsByService(name.getDisplayName());
        assertEquals(response, exceptedAddresses);
    }
}