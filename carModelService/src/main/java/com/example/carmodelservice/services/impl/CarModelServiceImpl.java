package com.example.carmodelservice.services.impl;

import com.example.carmodelservice.services.CarModelService;
import com.example.interfaces.dto.CarBrandDto;
import com.example.interfaces.dto.CarModelDto;
import com.example.interfaces.dto.EngineDto;
import com.example.interfaces.entities.CarBrand;
import com.example.interfaces.entities.CarModel;
import com.example.interfaces.entities.Engine;
import com.example.interfaces.entities.User;
import com.example.interfaces.models.Role;
import com.example.interfaces.repositories.CarBrandRepository;
import com.example.interfaces.repositories.CarModelRepository;
import com.example.interfaces.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository carModelRepository;

    private final CarBrandRepository carBrandRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public CarModelDto save(CarModelDto entity) {
        User user = userRepository.findByName(getAuthentication().getName());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CarModelDto carModel = new CarModelDto();

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(entity.getCarBrand().getId(), user.getId())){
            CarModel carModelMap = modelMapper.map(entity, CarModel.class);
            carModelRepository.save(carModelMap);
            carModel = getById(carModelMap.getId());
        }

        return carModel;
    }

    @Override
    public void deleteById(long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        Optional<CarModel> carModel = carModelRepository.findById(id);

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(carModel.get().getCarBrand().getId(), user.getId())){
            carModelRepository.deleteById(id);
        }
    }

    @Override
    public void deleteByEntity(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        Optional<CarModel> carModel = carModelRepository.findById(id);

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(carModel.get().getCarBrand().getId(), user.getId())){
            carModelRepository.delete(carModel.get());
        }
    }

    @Override
    public void deleteAll() {
        User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN)){
            carModelRepository.deleteAll();
        }

        else carModelRepository.deleteByIdCarBrand(user.getId());
    }

    @Override
    public CarModelDto update(CarModelDto entity, Long oldId) {
        User user = userRepository.findByName(getAuthentication().getName());
        Optional<CarModel> carModelOptional = carModelRepository.findById(oldId);
        CarModelDto carModel = new CarModelDto();

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(carModelOptional.get().getCarBrand().getId(), user.getId())){
            CarBrand carBrand = carBrandRepository.findById(entity.getCarBrand().getId()).get();
            CarModel carModelMap = modelMapper.map(entity, CarModel.class);
            carModelMap.setId(oldId);
            carModelMap.setCarBrand(carBrand);
            carModelRepository.save(carModelMap);
            carModel = getById(oldId);
        }

        return carModel;
    }

    @Override
    public CarModelDto getById(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CarModel carModel = carModelRepository.findById(id).get();
        CarModelDto carModelDto = new CarModelDto();

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(carModel.getCarBrand().getId(), user.getId())){
            carModelDto = modelMapper.map(carModel, CarModelDto.class);
        }

        return carModelDto;
    }

    @Override
    public List<CarModelDto> getAll() {
        User user = userRepository.findByName(getAuthentication().getName());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        if (user.getRole().equals(Role.ADMIN)){
            List<CarModel> carModels = carModelRepository.findAll();
            List<CarModelDto> carModelDto = carModels.stream().map(carModel -> modelMapper.map(carModel, CarModelDto.class)).collect(Collectors.toList());
            return carModelDto;
        }

        List<CarModel> carModels = carModelRepository.getAllById(user.getId());
        List<CarModelDto> carModelDto = carModels.stream().map(carModel -> modelMapper.map(carModel, CarModelDto.class)).collect(Collectors.toList());
        return carModelDto;
    }

    @Override
    public CarBrandDto getAllByVId(Long carModelId) {
        User user = userRepository.findByName(getAuthentication().getName());
        Optional<CarModel> carModel = carModelRepository.findById(carModelId);
        CarBrandDto carBrandDto = new CarBrandDto();

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(carModel.get().getCarBrand().getId(), user.getId())){
            CarBrand brand = carModelRepository.getAllByVId(carModel.get().getCarBrand().getId());
            carBrandDto = modelMapper.map(brand, CarBrandDto.class);
        }

        return carBrandDto;
    }

    @Override
    public EngineDto getAllByName(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        Optional<CarModel> carModel = carModelRepository.findById(id);
        EngineDto engineDto = new EngineDto();

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(carModel.get().getCarBrand().getId(), user.getId())){
            Engine engine = carModelRepository.getAllByName(id);
            engineDto = modelMapper.map(engine, EngineDto.class);
        }

        return engineDto;
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
