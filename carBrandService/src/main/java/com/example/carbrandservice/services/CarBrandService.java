package com.example.carbrandservice.services;



import com.example.interfaces.dto.CarBrandDto;
import com.example.interfaces.dto.CarBrandUserDto;

import java.util.List;

public interface CarBrandService {
    public CarBrandDto save(CarBrandUserDto entity);
    public void deleteById(long id, String userName);
    public void deleteByEntity(Long id, String userName);
    public void deleteAll(String userName);
    public CarBrandDto update(CarBrandDto entity, Long oldId, String userName);
    public CarBrandDto getById(long id, String userName);
    public List<CarBrandDto> getAll(String userName);
}


