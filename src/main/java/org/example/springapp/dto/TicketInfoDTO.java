package org.example.springapp.dto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TicketInfoDTO {
    private Long id;
    private BigDecimal price;
    private String currency;
    private Boolean availability;
    private Long attractionID;
}
