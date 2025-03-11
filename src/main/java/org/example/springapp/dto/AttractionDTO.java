package org.example.springapp.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.springapp.utils.enums.AttractionType;

import java.util.List;

@Getter
@Setter
public class AttractionDTO {
    private Long id;
    private String name;
    private String description;
    private AttractionType attractionType;
    private AddressDTO address;
    private TicketInfoDTO ticketInfo;
    private List<ServiceDTO> services;
}
