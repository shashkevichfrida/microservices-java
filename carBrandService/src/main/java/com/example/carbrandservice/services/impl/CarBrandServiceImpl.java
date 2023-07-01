package com.example.carbrandservice.services.impl;

import com.example.carbrandservice.services.CarBrandService;
import com.example.interfaces.dto.CarBrandDto;
import com.example.interfaces.dto.CarBrandUserDto;
import com.example.interfaces.entities.CarBrand;
import com.example.interfaces.entities.CarModel;
import com.example.interfaces.entities.User;
import com.example.interfaces.models.Role;
import com.example.interfaces.repositories.CarBrandRepository;
import com.example.interfaces.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarBrandServiceImpl implements CarBrandService {

    private final CarBrandRepository carBrandRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CarBrandDto save(CarBrandUserDto entity) {
        User user = userRepository.findByName(entity.getUserName());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CarBrand carBrand = modelMapper.map(entity.getCarBrandDto(), CarBrand.class);
        user = entityManager.merge(user);
        carBrand.setUser(user);
        carBrandRepository.save(carBrand);
        return getById(carBrand.getId(), entity.getUserName());
    }
    @Override
    public void deleteById(long id, String userName) {
        User user = userRepository.findByName(userName);

        if (user.getRole().equals(Role.ADMIN) || user.getId() == id){
            carBrandRepository.deleteById(id);
        }
    }

    @Override
    public void deleteByEntity(Long id, String userName) {
        User user = userRepository.findByName(userName);

        if (user.getRole().equals(Role.ADMIN) || user.getId() == id) {
            CarBrand carBrand = carBrandRepository.findById(id).get();
            carBrandRepository.delete(carBrand);
        }

    }
    @Override
    public void deleteAll(String userName) {
        carBrandRepository.deleteAll();

        User user = userRepository.findByName(userName);

        if (user.getRole().equals(Role.ADMIN)) {
            carBrandRepository.deleteAll();
        }

        else {
            carBrandRepository.deleteById(user.getId());
        }
    }

    @Transactional
    @Override
    public CarBrandDto update(CarBrandDto entity, Long oldId, String userName) {
        User user = userRepository.findByName(userName);

        if (user.getRole().equals(Role.ADMIN) || Objects.equals(user.getId(), oldId)) {
            CarBrand carBrand = carBrandRepository.findById(oldId).get();
            carBrand.setName(entity.getName());
            carBrand.setDate(entity.getDate());
            carBrandRepository.save(carBrand);
        }

        return getById(oldId, userName);
    }

    @Transactional
    @Override
    public CarBrandDto getById(long id, String userName) {
        CarBrandDto carBrandDto = new CarBrandDto();
        User user = userRepository.findByName(userName);

        if (user.getRole().equals(Role.ADMIN) || user.getId() == id) {

            CarBrand carBrand = carBrandRepository.findById(id).get();

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            carBrandDto = modelMapper.map(carBrand, CarBrandDto.class);
        }

        return carBrandDto;
    }


    @Transactional
    @Override
    public List<CarBrandDto> getAll(String userName){
        User user = userRepository.findByName(userName);

        if (user.getRole().equals(Role.ADMIN)) {
            List<CarBrand> carBrands = carBrandRepository.findAll();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            List<CarBrandDto> carBrandDto = carBrands.stream().map(carBrand -> modelMapper.map(carBrand, CarBrandDto.class)).collect(Collectors.toList());
            return carBrandDto;
        }

        CarBrandDto carBrandDto = getById(user.getId(), userName);
        List<CarBrandDto> cars = new ArrayList<>();
        cars.add(carBrandDto);

        return cars;
    }
    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }



}

