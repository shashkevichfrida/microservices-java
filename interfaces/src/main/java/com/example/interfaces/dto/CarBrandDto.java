package com.example.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarBrandDto {
    private Long id;
    private String name;
    private Date date;
    private List<CarModelDto> carModelId = new ArrayList<>();

}
