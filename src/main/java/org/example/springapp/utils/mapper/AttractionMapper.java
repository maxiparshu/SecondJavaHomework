package org.example.springapp.utils.mapper;

import org.example.springapp.dto.AttractionDTO;
import org.example.springapp.model.Attraction;
import org.example.springapp.utils.enums.AttractionType;
import org.springframework.stereotype.Component;

/**
 * Класс для преобразования
 */
@Component
public class AttractionMapper extends EntityMapper<Attraction, AttractionDTO> {
    public Attraction toEntity(AttractionDTO attraction) {
        return Attraction.builder()
                .id(attraction.getId())
                .description(attraction.getDescription())
                .name(attraction.getName())
                .attractionType(AttractionType.fromDisplayName(attraction.getAttractionType()))
                .build();
    }
}
