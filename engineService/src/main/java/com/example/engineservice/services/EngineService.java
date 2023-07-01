package com.example.engineservice.services;


import com.example.interfaces.dto.CarModelDto;
import com.example.interfaces.dto.EngineDto;

import java.util.List;

public interface EngineService {
    public EngineDto save(EngineDto entity);
    public void deleteById(Long id);
    public void deleteByEntity(Long id);
    public void deleteAll();
    public EngineDto update(EngineDto entity, Long oldId);
    public EngineDto getById(Long id);
    public List<EngineDto> getAll();
    public List<CarModelDto> getAllByVId(Long entityId);
}
