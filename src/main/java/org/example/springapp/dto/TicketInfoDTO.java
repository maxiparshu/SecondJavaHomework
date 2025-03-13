package org.example.springapp.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO для передачи информации о билетах.
 */
@Data
@Builder
public class TicketInfoDTO {

    private Long id;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater 0")
    private BigDecimal price;

    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must consist from 3 symbols (example USD)")
    private String currency;

    @NotNull(message = "Availability cannot be null")
    private Boolean availability;

    @NotNull(message = "ID cannot be null")
    @Positive(message = "ID cannot be positive")
    private Long attractionID;
}
