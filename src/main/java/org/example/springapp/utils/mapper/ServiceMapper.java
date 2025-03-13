package org.example.springapp.utils.mapper;

import org.example.springapp.dto.ServiceDTO;
import org.example.springapp.model.Service;
import org.example.springapp.utils.enums.ServiceType;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования {@link Service} и {@link ServiceDTO}
 */
@Component
public class ServiceMapper implements Mapper<Service, ServiceDTO> {
    @Override
    public Service toEntity(ServiceDTO dto) {
        return Service.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .serviceType(ServiceType.fromDisplayName(dto.getServiceType()))
                .build();
    }
}
