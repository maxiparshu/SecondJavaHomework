package org.example.springapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сущность, представляющая информацию о билете для достопримечательности.
 */
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_info", schema = "tourism")
public class TicketInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "currency")
    private String currency;

    @Column(name = "availability")
    private Boolean availability;

    /**
     * Связь с достопримечательностью, для которой предоставляется информация о билете.
     **/
    @OneToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;
}