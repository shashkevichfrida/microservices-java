package com.example.interfaces.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carModel")
@Getter
@Setter
@NoArgsConstructor
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(name="Name")
    private String name;

    @Column(name="BodyType")
    private String bodyType;

    @Column(name="Length")
    private Double length;

    @Column(name="Weight")
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "carBrandId")
    @JsonIgnore
    private CarBrand carBrand;

    @Column(name="Height")
    private Double height;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "carModelId", nullable = true)
    @JsonIgnore
    private Engine engine;
}
