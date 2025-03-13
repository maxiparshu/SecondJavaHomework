package org.example.springapp.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * DTO для передачи информации о услугах.
 */
@Data
@Builder
public class ServiceDTO {
    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
    private String serviceType;
    @NotNull(message = "The list of attractions cannot be empty")
    @Size(min = 1, message = "The list of attractions must contain at least one value")
    private List<@Positive(message = "Attraction ID must be a positive number") Long> attractionsID;

}
