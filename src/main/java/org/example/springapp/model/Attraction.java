package org.example.springapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.springapp.utils.enums.AttractionType;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(mappedBy = "attraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private TicketInfo ticketInfo;

    @ManyToMany
    @JoinTable(
            name = "attraction_service", schema = "tourism",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services;

}
