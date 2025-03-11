package org.example.springapp.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class TicketInfoDTO {
    private Long id;
    private BigDecimal price;
    private String currency;
    private Boolean availability;
}
