package org.example.springapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO для передачи информации об аттракционах.
 */
@Data
@Builder
public class AttractionDTO {
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
    private String attractionType;
    @NotNull(message = "Address ID cannot be null")
    @Min(value = 1, message = "Address ID must be at least 1")
    private Long addressID;
    private Long ticketInfoID;
    private List<Long> servicesID;
}
