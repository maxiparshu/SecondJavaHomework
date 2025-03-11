package org.example.springapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Сущность, представляющая информацию о билете для достопримечательности.
 */
@Setter
@Getter
@Entity
@Table(name = "ticket_info", schema = "tourism")
public class TicketInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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