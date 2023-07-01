package com.example.externinteface.controllers;

import com.example.interfaces.dto.CarBrandDto;
import com.example.interfaces.dto.CarBrandUserDto;
import com.example.interfaces.dto.UserDto;
import com.example.interfaces.entities.CarBrand;
import com.example.interfaces.entities.User;
import com.example.interfaces.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/carBrand")
@Tag(name = "carBrand", description = "car brand APIs")
@PreAuthorize("hasAnyAuthority('developers:read', 'developers:write')")
@AllArgsConstructor
public class CarBrandController {

    private final RabbitTemplate rabbitTemplate;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;


    @PostMapping("/save")
    public CarBrandDto save(@RequestBody CarBrandDto carBrandDto) throws JsonProcessingException {
        String userName = getAuthentication().getName();

        CarBrandUserDto carBrandUserDto = new CarBrandUserDto(carBrandDto, userName);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(carBrandUserDto);

        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueCarBrandResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeCarBrand", "saveCarBrand", message);

        Message resultMessage = rabbitTemplate.receive("queueCarBrandResult", 5000);

        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }

        String body = new String(resultMessage.getBody());
        CarBrandDto brandRes = new ObjectMapper().readValue(body, CarBrandDto.class);

        return brandRes;
    }

    /*@PostMapping("/getAll")
    public List<CarBrandDto> getAll(){
        return carBrandServices.getAll();
    }*/

    @PostMapping("/getById")
    public CarBrandDto getById(@RequestParam("id") Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        CarBrandDto carBrandDto = new CarBrandDto();
        carBrandDto.setId(id);
        String jsonString = objectMapper.writeValueAsString(carBrandDto);
        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueCarBrandResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeGetCarBrand", "getByIdCarBrand", message);

        Message resultMessage = rabbitTemplate.receive("queueCarBrandResult", 5000);
        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }
        try {
            CarBrandDto carBrand = objectMapper.readValue(resultMessage.getBody(), CarBrandDto.class);
            return carBrand;
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize result message", e);
        }
    }

    @PostMapping("/deleteAll")
    public void deleteAll() {
        rabbitTemplate.convertAndSend("exchangeDeleteAllCarBrands", "deleteAllCarBrands", "");
    }
    @PostMapping("/update")
    public CarBrandDto update(@RequestParam("OldId") Long oldId, @RequestBody CarBrandDto carBrandDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(carBrandDto);

        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueCarBrandResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeUpdateCarBrand", "updateCarBrand", message);

        Message resultMessage = rabbitTemplate.receive("queueCarBrandResult", 5000);

        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }

        String body = new String(resultMessage.getBody());
        CarBrandDto carBrandRes = new ObjectMapper().readValue(body, CarBrandDto.class);

        return carBrandRes;
    }

    @PostMapping("/deleteByEntity")
    public void deleteByEntity(@RequestParam("id") Long id) {
        rabbitTemplate.convertAndSend("exchangeDeleteCarBrand", "deleteByEntityCarBrand", id);
    }

    @PostMapping("/deleteById")
    public void deleteById(@RequestParam("id") Long id) {
        rabbitTemplate.convertAndSend("exchangeDeleteCarBrand", "deleteByIdCarBrand", id);
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
