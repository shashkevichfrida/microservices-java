package com.example.carmodelservice.services;



import com.example.interfaces.dto.CarBrandDto;
import com.example.interfaces.dto.CarModelDto;
import com.example.interfaces.dto.EngineDto;

import java.util.List;

public interface CarModelService {
    public CarModelDto save(CarModelDto entity);
    public void deleteById(long id);
    public void deleteByEntity(Long id);
    public void deleteAll();
    public CarModelDto update(CarModelDto entity, Long oldId);
    public CarModelDto getById(Long id);
    public List<CarModelDto> getAll();
    public CarBrandDto getAllByVId(Long carModelId);
    public EngineDto getAllByName(Long id);
}

