package org.example.springapp.dto;

import lombok.Builder;
import lombok.Data;
import org.example.springapp.utils.enums.AttractionType;

import java.util.List;

@Data
@Builder
public class AttractionDTO {
    private Long id;
    private String name;
    private String description;
    private AttractionType attractionType;
    private AddressDTO address;
    private Long ticketInfoID;
    private List<Long> servicesID;
}
