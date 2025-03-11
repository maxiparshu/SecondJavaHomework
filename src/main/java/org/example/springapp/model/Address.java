package org.example.springapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Сущность, представляющая информацию о адресе.
 */
@Setter
@Getter
@Entity
@Table(name = "address", schema = "tourism")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name= "building")
    private Integer building;

    @Column(name= "street")
    private String street;
    @Column(name= "region")
    private String region;
    @Column(name = "city")
    private String city;

    @Column(name= "latitude")
    private Double latitude;
    @Column(name= "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "address")
    private List<Attraction> attraction;
}
