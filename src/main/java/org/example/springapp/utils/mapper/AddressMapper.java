package org.example.springapp.utils.mapper;

import org.example.springapp.dto.AddressDTO;
import org.example.springapp.model.Address;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования {@link Address} и {@link AddressDTO}
 */
@Component
public class AddressMapper implements Mapper<Address, AddressDTO> {
    @Override
    public Address toEntity(AddressDTO dto) {
        return Address.builder()
                .id(dto.getId())
                .building(dto.getBuilding())
                .city(dto.getCity())
                .street(dto.getStreet())
                .region(dto.getRegion())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
    }
}
