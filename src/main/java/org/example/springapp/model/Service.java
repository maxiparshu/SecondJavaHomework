package org.example.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.springapp.utils.enums.ServiceType;

import java.util.List;

/**
 * Сущность, представляющая услугу, доступную в рамках туристических достопримечательностей.
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service", schema = "tourism")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    /**
     * Список достопримечательностей, в которых доступна данная услуга.
     * Связь "многие ко многим", управляемая со стороны сущности Attraction.
     */
    @ManyToMany(mappedBy = "services")
    @JsonBackReference
    private List<Attraction> attractions;

}
