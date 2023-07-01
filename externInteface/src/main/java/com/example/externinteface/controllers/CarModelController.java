package com.example.externinteface.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/carModel")
@Tag(name = "carModel", description = "car model APIs")
@PreAuthorize("hasAnyAuthority('developers:read', 'developers:write')")
public class CarModelController {
    /*private final CarModelServiceImpl carModelService;

    public CarModelController( CarModelServiceImpl carModelService) {
        this.carModelService = carModelService;
    }

    @PostMapping("/save")
    public CarModelDto save(@RequestBody CarModelDto carModelDto) {
        return carModelService.save(carModelDto);
    }

    @PostMapping("/getAll")
    public List<CarModelDto> getAll() {
        return carModelService.getAll();

    }

    @PostMapping("/getById")
    public CarModelDto getById(@RequestParam("id") Long id) {
        return carModelService.getById(id);
    }

    @PostMapping("/deleteAll")
    public String deleteAll() {
        carModelService.deleteAll();
        return "entity deleted";
    }

    @PostMapping("/update")
    public CarModelDto update(@RequestBody CarModelDto carModelDto, @RequestParam("OldId") Long oldId) {
        return carModelService.update(carModelDto, oldId);
    }

    @PostMapping("/deleteByEntity")
    public void deleteByEntity(@RequestParam("id") Long id) {
        carModelService.deleteByEntity(id);
    }

    @PostMapping("/deleteById")
    public void deleteById(@RequestParam("id") Long id) {
        carModelService.deleteById(id);
    }

    @PostMapping("/getAllByVId")
    public CarBrandDto getAllByVId(@RequestParam("id") Long id) {
        return carModelService.getAllByVId(id);
    }

    @PostMapping("/getAllByName")
    public EngineDto getAllByName(@RequestParam("id") Long id) {
        return carModelService.getAllByName(id);
    }
*/}
