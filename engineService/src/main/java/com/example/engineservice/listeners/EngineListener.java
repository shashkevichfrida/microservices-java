package com.example.engineservice.controllers;

import com.example.engineservice.services.impl.EngineServiceImpl;
import com.example.interfaces.dto.CarModelDto;
import com.example.interfaces.dto.EngineDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/engine")
//@Tag(name = "engine", description = "engine APIs")
@PreAuthorize("hasAnyAuthority('developers:read', 'developers:write')")
@AllArgsConstructor
public class EngineController {

    private final EngineServiceImpl engineService;

    @PostMapping ("/save")
    public EngineDto save(@RequestBody EngineDto engineDto) {
        return engineService.save(engineDto);
    }

    @PostMapping("/getAll")
    public List<EngineDto> getAll(){
        return engineService.getAll();
    }

    @PostMapping("/getById")
    public EngineDto getById(@RequestParam("id") Long id) {
        return engineService.getById(id);
    }

    @PostMapping("/deleteAll")
    public List<EngineDto> deleteAll() {
        engineService.deleteAll();
        return engineService.getAll();
    }
    @PostMapping("/update")
    public EngineDto update(@RequestParam("OldId") Long oldId, @RequestBody EngineDto engineDto) {
        return engineService.update(engineDto, oldId);
    }

    @PostMapping("/deleteByEntity")
    public void deleteByEntity(@RequestParam("id") Long id) {
        engineService.deleteByEntity(id);
    }

    @PostMapping("/deleteById")
    public void deleteById(@RequestParam("id") Long id) {
        engineService.deleteById(id);
    }

    @PostMapping("/getAllByVId")
    public List<CarModelDto> getAllByVId(@RequestParam("id") Long id) {
        return engineService.getAllByVId(id);
    }
}
