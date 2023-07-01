package com.example.engineservice.services.impl;

import com.example.engineservice.services.EngineService;
import com.example.interfaces.dto.CarModelDto;
import com.example.interfaces.dto.EngineDto;
import com.example.interfaces.entities.CarModel;
import com.example.interfaces.entities.Engine;
import com.example.interfaces.entities.User;
import com.example.interfaces.models.Role;
import com.example.interfaces.repositories.EngineRepository;
import com.example.interfaces.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EngineServiceImpl implements EngineService {

    private final EngineRepository engineRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;
    @Override
    public EngineDto save(EngineDto entity) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = userRepository.findByName(getAuthentication().getName());
        Engine engine = modelMapper.map(entity, Engine.class);
        List<CarModel> carModels = engineRepository.getAllByVId(engine.getId());
        EngineDto engineDto = new EngineDto();

        if (user.getRole().equals(Role.ADMIN) || carModels.stream().allMatch(eng -> eng.getCarBrand().getId().equals(user.getId()))){
            engineRepository.save(engine);
            engineDto = getById(engine.getId());
        }

        return engineDto;
    }

    @Override
    public void deleteById(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        List<CarModel> carModels = engineRepository.getAllByVId(id);

        if (user.getRole().equals(Role.ADMIN) || carModels.stream().allMatch(eng -> eng.getCarBrand().getId().equals(user.getId()))){
            engineRepository.deleteById(id);
        }
    }


    @Override
    public void deleteByEntity(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        List<CarModel> carModels = engineRepository.getAllByVId(id);

        if (user.getRole().equals(Role.ADMIN) || carModels.stream().allMatch(eng -> eng.getCarBrand().getId().equals(user.getId()))){
            Engine engine = engineRepository.findById(id).get();
            engineRepository.delete(engine);
        }
    }

    @Override
    public void deleteAll() {
        User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN)){
            engineRepository.deleteAll();
        }
        else {
            List<Long> enginesToRemove = engineRepository.findEnginesToRemove(user.getId());
            engineRepository.removeEngineFromCarModels(user.getId());
            engineRepository.deleteEnginesByIds(enginesToRemove);
        }
    }

    @Override
    public EngineDto update(EngineDto entity, Long oldId) {
        User user = userRepository.findByName(getAuthentication().getName());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        List<CarModel> carModels = engineRepository.getAllByVId(oldId);
        EngineDto engineDto = new EngineDto();

        if (user.getRole().equals(Role.ADMIN) || carModels.stream().allMatch(eng -> eng.getCarBrand().getId().equals(user.getId()))){
            Engine engine = modelMapper.map(entity, Engine.class);
            engine.setCarModelId(carModels);
            engine.setId(oldId);
            engineRepository.save(engine);
            engineDto = getById(oldId);
        }

        return engineDto;
    }

    @Override
    public EngineDto getById(Long id) {
        User user = userRepository.findByName(getAuthentication().getName());
        Engine engine = engineRepository.findById(id).get();
        List<CarModel> carModels = engineRepository.getAllByVId(id);
        EngineDto engineDto = new EngineDto();

        if (user.getRole().equals(Role.ADMIN) || carModels.stream().allMatch(eng -> eng.getCarBrand().getId().equals(user.getId()))){
            engineDto = modelMapper.map(engine, EngineDto.class);
        }

        return engineDto;
    }

    @Override
    public List<EngineDto> getAll() {
        User user = userRepository.findByName(getAuthentication().getName());

        if (user.getRole().equals(Role.ADMIN)){
            List<Engine> engines = engineRepository.findAll();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            List<EngineDto> engineDto = engines.stream().map(engine -> modelMapper.map(engine, EngineDto.class)).collect(Collectors.toList());
            return engineDto;
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Engine> engines = engineRepository.getAllById(user.getId());
        List<EngineDto> engineDto = engines.stream().map(engine -> modelMapper.map(engine, EngineDto.class)).collect(Collectors.toList());
        return engineDto;
    }

    @Override
    public List<CarModelDto> getAllByVId(Long entityId) {
        User user = userRepository.findByName(getAuthentication().getName());
        List<CarModel> carModels = engineRepository.getAllByVId(entityId);
        List<CarModelDto> carModelDto = new ArrayList<>();

        if (user.getRole().equals(Role.ADMIN) || carModels.stream().allMatch(eng -> eng.getCarBrand().getId().equals(user.getId()))){
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            carModelDto = carModels.stream().map(carModel -> modelMapper.map(carModel, CarModelDto.class)).collect(Collectors.toList());
        }

        return carModelDto;
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

