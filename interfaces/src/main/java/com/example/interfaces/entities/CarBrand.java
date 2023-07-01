package com.example.interfaces.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carBrand")
@Getter
@Setter
@NoArgsConstructor
public class CarBrand {
    @Id
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Date")
    private Date date;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "carBrandId")
    @JsonIgnore
    private List<CarModel> carModelId = new ArrayList<CarModel>();

    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
