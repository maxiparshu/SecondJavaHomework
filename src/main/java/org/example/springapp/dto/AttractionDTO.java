package org.example.springapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.springapp.utils.enums.AttractionType;

import java.util.List;

@Getter
@Setter
@Builder
public class AttractionDTO {
    private Long id;
    private String name;
    private String description;
    private AttractionType attractionType;
    private AddressDTO address;
    private TicketInfoDTO ticketInfo;
    private List<ServiceDTO> services;
}
