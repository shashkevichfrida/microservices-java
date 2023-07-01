package com.example.interfaces.entities;

import com.example.interfaces.entities.CarModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "car_engine")
@NoArgsConstructor
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="Id")
    private Long id;

    @Column(name="Name")
    private String name;

    @Column(name="Volume")
    private int volume;

    @Column(name="Number_Of_Cylinders")
    private int numberOfCylinders;

    @Column(name="Height")
    private Double height;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "carModelId")
    @JsonIgnore
    private List<CarModel> carModelId = new ArrayList<CarModel>();
}
