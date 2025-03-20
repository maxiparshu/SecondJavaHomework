package org.example.springapp.controller;

import org.example.springapp.dto.ServiceDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Attraction;
import org.example.springapp.model.Service;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.service.impl.ServiceService;
import org.example.springapp.utils.mapper.EntityByIDMapper;
import org.example.springapp.utils.mapper.ServiceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceControllerTest {
    @Mock
    private ServiceService serviceService;
    @Mock
    private ServiceMapper serviceMapper;
    @Mock
    private AttractionService attractionService;
    @InjectMocks
    private ServiceController serviceController;

    @Test
    void readAllSuccess() {
        var service1 = Service.builder().name("test1").build();
        var service2 = Service.builder().name("test2").build();
        var expectedAddresses = List.of(service1, service2);

        when(serviceService.read()).thenReturn(expectedAddresses);

        var result = serviceController.readAll();

        assertEquals(expectedAddresses, result);

        verify(serviceService, times(1)).read();
    }

    @Test
    void getServiceByIdSuccess() throws ResourceNotFoundException {
        var expectedService = Service.builder().name("test1").build();
        var id = 228L;
        when(serviceService.getByID(id)).thenReturn(expectedService);

        var result = serviceController.getServiceById(id);

        assertEquals(expectedService, result.getBody());
    }

    @Test
    void getServiceByIdNotFound() throws ResourceNotFoundException {
        var id = 228L;
        when(serviceService.getByID(id)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> serviceController.getServiceById(id));
    }

    @Test
    void createServiceSuccess() throws ResourceNotFoundException {
        var ids = List.of(1L);
        var attr = Attraction.builder().id(1L).name("test1").build();
        var dto = ServiceDTO.builder().name("test1").attractionsID(ids).build();
        var exceptedService = Service.builder().name("test1").attractions(List.of(attr)).build();

        when(serviceMapper.toEntity(dto)).thenReturn(exceptedService);
        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenReturn(List.of(attr));

            var status = serviceController.createService(dto);

            verify(serviceService, times(1)).create(exceptedService);
            assertEquals(HttpStatus.CREATED, status);
        }
    }

    @Test
    void createAddressFailed() {
        var ids = List.of(999L);
        var dto = ServiceDTO.builder().name("test1").attractionsID(ids).build();

        when(serviceMapper.toEntity(dto)).thenReturn(Service.builder().name("test1").build());

        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenThrow(new ResourceNotFoundException("Attractions with these IDs don't exist: " + ids));

            var exception = assertThrows(ResourceNotFoundException.class, () -> serviceController.createService(dto));

            assertEquals("Attractions with these IDs don't exist: [999]", exception.getMessage());
            verify(serviceService, never()).create(any(Service.class));
        }
    }

    @Test
    void createAddressFailedWithoutAttractions() throws ResourceNotFoundException {
        var dto = ServiceDTO.builder().name("test1").id(1L).build();
        var exceptedService = Service.builder().name("test1").attractions(new ArrayList<>()).id(1L).build();
        when(serviceMapper.toEntity(dto)).thenReturn(exceptedService);
        var status = serviceController.createService(dto);

        verify(serviceService, times(1)).create(exceptedService);
        assertEquals(HttpStatus.CREATED, status);
    }

    @Test
    void updateServiceSuccess() throws ResourceNotFoundException {
        var ids = List.of(1L);
        var attr = Attraction.builder().id(1L).name("test1").build();
        var dto = ServiceDTO.builder().name("test1").id(1L).attractionsID(ids).build();
        var exceptedService = Service.builder().name("test1").id(1L).attractions(List.of(attr)).build();

        when(serviceMapper.toEntity(dto)).thenReturn(exceptedService);
        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenReturn(List.of(attr));

            var status = serviceController.updateService(dto);

            verify(serviceService, times(1)).update(exceptedService);

            assertEquals(HttpStatus.OK, status);
        }
    }

    @Test
    void updateAddressFailed() throws ResourceNotFoundException {
        var dto = ServiceDTO.builder().name("test1").id(1L).build();
        var exceptedService = Service.builder().name("test1").id(1L).build();
        when(serviceMapper.toEntity(dto)).thenReturn(exceptedService);
        doThrow(ResourceNotFoundException.class)
                .when(serviceService).update(exceptedService);
        assertThrows(ResourceNotFoundException.class, () -> serviceController.updateService(dto));
    }

    @Test
    void deleteServiceSuccess() throws ResourceNotFoundException {
        var id = 228L;

        var result = serviceController.deleteService(id);

        verify(serviceService, times(1)).delete(id);
        assertEquals(result, HttpStatus.OK);
    }

    @Test
    void deleteServiceNotFound() throws ResourceNotFoundException {
        var id = 228L;
        doThrow(ResourceNotFoundException.class)
                .when(serviceService).delete(id);
        assertThrows(ResourceNotFoundException.class, () -> serviceController.deleteService(id));
    }
}