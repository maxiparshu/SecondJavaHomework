package org.example.springapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long id;
    private Integer building;
    private String street;
    private String region;
    private String city;
    private Double latitude;
    private Double longitude;
}
