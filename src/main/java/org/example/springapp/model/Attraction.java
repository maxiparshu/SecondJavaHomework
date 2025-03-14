package org.example.springapp.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import org.example.springapp.utils.enums.AttractionType;

import java.util.List;

/**
 * Сущность, представляющая туристическую достопримечательность.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Table(name = "attraction", schema = "tourism")
public class Attraction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "attraction_type")
    private AttractionType attractionType;

    /**
     * Адрес достопримечательности.
     * Связь "многие к одному" с сущностью Address.
     * Ленивая загрузка для оптимизации производительности.
     * Каскадные операции: сохранение и обновление адреса вместе с достопримечательностью.
     */
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    /**
     * Информация о билетах к достопримечательности.
     * Связь "один к одному", удаляется вместе с достопримечательностью.
     */
    @JsonManagedReference
    @OneToOne(mappedBy = "attraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private TicketInfo ticketInfo;

    /**
     * Список услуг, доступных на территории достопримечательности.
     * Реализована связь "многие ко многим" через промежуточную таблицу "attraction_service".
     */
    @ManyToMany
    @JoinTable(
            name = "attraction_service", schema = "tourism",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @JsonBackReference
    private List<Service> services;

}
