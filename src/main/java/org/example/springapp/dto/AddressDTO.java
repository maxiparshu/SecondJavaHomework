package org.example.springapp.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO для передачи информации об адресе.
 */
@Data
@Builder
public class AddressDTO {
    private Long id;

    @Min(value = 1, message = "Building number must be at least 1")
    private Integer building;

    @Size(max = 100, message = "Street name must be less than 100 characters")
    private String street;
    @NotBlank(message = "Region cannot be blank")
    @Size(max = 50, message = "Region name must be less than 50 characters")
    private String region;
    @NotBlank(message = "Region cannot be blank")
    @Size(max = 50, message = "Region name must be less than 50 characters")
    private String city;
    @NotNull(message = "Latitude cannot be null")
    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    private Double latitude;
    @NotNull(message = "Longitude cannot be null")
    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    private Double longitude;
    private List<Long> attractionID;
}
