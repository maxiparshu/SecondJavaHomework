package org.example.springapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Сущность, представляющая информацию об адресе.
 */
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address", schema = "tourism")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "building")
    private Integer building;

    @Column(name = "street")
    private String street;

    @Column(name = "region")
    private String region;

    @Column(name = "city")
    private String city;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    /**
     * Список достопримечательностей, расположенных по данному адресу.
     * Связь "один ко многим" с сущностью Attraction.
     */
    @OneToMany(mappedBy = "address")
    @JsonBackReference
    private List<Attraction> attraction;

}
