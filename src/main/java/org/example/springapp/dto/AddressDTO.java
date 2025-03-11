package org.example.springapp.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AddressDTO {
    private Long id;
    private Integer building;
    private String street;
    private String region;
    private String city;
    private Double latitude;
    private Double longitude;
    private List<AttractionDTO> attraction;
}
