package org.example.springapp.controller;

import org.example.springapp.dto.AddressDTO;
import org.example.springapp.exception.ResourceNotFoundException;
import org.example.springapp.model.Address;
import org.example.springapp.model.Attraction;
import org.example.springapp.service.impl.AddressService;
import org.example.springapp.service.impl.AttractionService;
import org.example.springapp.utils.mapper.AddressMapper;
import org.example.springapp.utils.mapper.EntityByIDMapper;
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
class AddressControllerTest {
    @Mock
    private AddressService addressService;
    @Mock
    private AttractionService attractionService;
    @Mock
    private AddressMapper addressMapper;
    @InjectMocks
    private AddressController addressController;

    @Test
    void shouldReturnAll() {
        var address1 = Address.builder().city("test1").build();
        var address2 = Address.builder().city("test2").build();
        var expectedAddresses = List.of(address1, address2);

        when(addressService.read()).thenReturn(expectedAddresses);

        var result = addressController.readAll();

        assertEquals(expectedAddresses, result);

        verify(addressService, times(1)).read();
    }

    @Test
    void shouldReturnByID() throws ResourceNotFoundException {
        var id = 1L;
        var exceptedAddress = Address.builder().city("test1").build();
        when(addressService.getByID(id)).thenReturn(exceptedAddress);
        var response = addressController.getAddressById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), exceptedAddress);

    }

    @Test
    void shouldThrowExceptionByID() throws ResourceNotFoundException {
        var id = 1L;
        when(addressService.getByID(id)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> addressController.getAddressById(id));
    }

    @Test
    void shouldCreateAddress() throws ResourceNotFoundException {
        var dto = AddressDTO.builder().city("test1").build();
        var exceptedAddress = Address.builder().city("test1").attraction(new ArrayList<>()).build();
        when(addressMapper.toEntity(dto)).thenReturn(exceptedAddress);
        var status = addressController.createAddress(dto);
        verify(addressService, times(1)).create(exceptedAddress);
        assertEquals(status, HttpStatus.CREATED);
    }

    @Test
    void shouldCreateAddressWithAttraction() throws ResourceNotFoundException {
        var ids = List.of(1L);
        var attr = Attraction.builder().id(1L).name("test1").build();
        var dto = AddressDTO.builder().city("test1").attractionID(ids).build();
        var exceptedAddress = Address.builder().city("test1").attraction(List.of(attr)).build();

        when(addressMapper.toEntity(dto)).thenReturn(exceptedAddress);
        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenReturn(List.of(attr));

            // Вызов контроллера
            var status = addressController.createAddress(dto);

            // Проверка, что сервис сохранил адрес
            verify(addressService, times(1)).create(exceptedAddress);

            // Проверка статуса
            assertEquals(HttpStatus.CREATED, status);
        }
    }

    @Test
    void shouldThrowExceptionInCreateAddress() {
        var ids = List.of(999L);
        var dto = AddressDTO.builder().city("test1").attractionID(ids).build();

        when(addressMapper.toEntity(dto)).thenReturn(Address.builder().city("test1").build());

        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenThrow(new ResourceNotFoundException("Attractions with these IDs don't exist: " + ids));

            var exception = assertThrows(ResourceNotFoundException.class, () -> addressController.createAddress(dto));

            assertEquals("Attractions with these IDs don't exist: [999]", exception.getMessage());

            verify(addressService, never()).create(any(Address.class));
        }
    }

    @Test
    void deleteAddressSuccess() throws ResourceNotFoundException {
        Long addressId = 1L;

        var status = addressController.deleteAddress(addressId);

        verify(addressService, times(1)).delete(addressId);

        assertEquals(HttpStatus.OK, status);
    }

    @Test
    void deleteAddressException() throws ResourceNotFoundException {
        Long invalidId = 999L;

        doThrow(new ResourceNotFoundException("Address not found"))
                .when(addressService).delete(invalidId);

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> addressController.deleteAddress(invalidId));

        assertEquals("Address not found", exception.getMessage());

        verify(addressService, times(1)).delete(invalidId);
    }

    @Test
    void updateAddressSuccess() throws ResourceNotFoundException {
        var dto = AddressDTO.builder().city("test1").id(2L).build();
        var exceptedAddress = Address.builder().city("test1").id(2L).build();
        when(addressMapper.toEntity(dto)).thenReturn(exceptedAddress);
        var status = addressController.updateAddress(dto);
        verify(addressService, times(1)).update(exceptedAddress);
        assertEquals(status, HttpStatus.OK);
    }

    @Test
    void updateAddressInvalidID() throws ResourceNotFoundException {
        var dto = AddressDTO.builder().city("test1").id(2L).build();
        var exceptedAddress = Address.builder().city("test1").id(2L).build();
        when(addressMapper.toEntity(dto)).thenReturn(exceptedAddress);
        doThrow(ResourceNotFoundException.class)
                .when(addressService).update(exceptedAddress);
        assertThrows(ResourceNotFoundException.class, () -> addressController.updateAddress(dto));
    }

    @Test
    void shouldUpdateAddressWithAttraction() throws ResourceNotFoundException {
        var ids = List.of(1L);
        var attr = Attraction.builder().id(1L).name("test1").build();
        var dto = AddressDTO.builder().city("test1").attractionID(ids).build();
        var exceptedAddress = Address.builder().city("test1").attraction(List.of(attr)).build();

        when(addressMapper.toEntity(dto)).thenReturn(exceptedAddress);
        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenReturn(List.of(attr));

            // Вызов контроллера
            var status = addressController.updateAddress(dto);

            // Проверка, что сервис сохранил адрес
            verify(addressService, times(1)).update(exceptedAddress);

            // Проверка статуса
            assertEquals(HttpStatus.OK, status);
        }
    }

    @Test
    void shouldThrowExceptionInUpdateAddressWithAttraction() throws ResourceNotFoundException {
        var ids = List.of(999L);
        var dto = AddressDTO.builder().city("test1").attractionID(ids).build();

        when(addressMapper.toEntity(dto)).thenReturn(Address.builder().city("test1").build());

        try (var mockStatic = mockStatic(EntityByIDMapper.class)) {
            mockStatic.when(() -> EntityByIDMapper.fetchByIds(ids, attractionService, "Attraction"))
                    .thenThrow(new ResourceNotFoundException("Attractions with these IDs don't exist: " + ids));

            var exception = assertThrows(ResourceNotFoundException.class, () -> addressController.updateAddress(dto));

            assertEquals("Attractions with these IDs don't exist: [999]", exception.getMessage());

            verify(addressService, never()).update(any(Address.class));
        }
    }
}