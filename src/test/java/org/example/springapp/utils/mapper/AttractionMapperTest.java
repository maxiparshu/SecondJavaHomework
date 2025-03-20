package org.example.springapp.utils.mapper;

import org.example.springapp.dto.AttractionDTO;
import org.example.springapp.model.Attraction;
import org.example.springapp.utils.enums.AttractionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class AttractionMapperTest {
    private AttractionMapper attractionMapper;
    private AttractionDTO dto;
    @BeforeEach
    void setUp(){
        attractionMapper = new AttractionMapper();
        dto = AttractionDTO.builder()
                .id(5L)
                .name("Test")
                .description("Description")
                .attractionType("Храм")
                .build();
    }
    @Test
    void toEntitySuccess() {
        var exceptedEntity = Attraction.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .attractionType(AttractionType.TEMPLE)
                .build();
        try (var mockStatic = mockStatic(AttractionType.class)) {
            mockStatic.when(()->AttractionType.fromDisplayName(dto.getAttractionType()))
                    .thenReturn(AttractionType.TEMPLE);
            var actualEntity = attractionMapper.toEntity(dto);

            assertEquals(actualEntity.getName(), exceptedEntity.getName());
            assertEquals(actualEntity.getAttractionType(), exceptedEntity.getAttractionType());
            assertEquals(actualEntity.getId(), exceptedEntity.getId());
        }
    }
}