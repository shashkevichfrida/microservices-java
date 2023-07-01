package com.example.externinteface.controllers;

import com.example.interfaces.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {
    private final RabbitTemplate rabbitTemplate;

    private final ModelMapper modelMapper;


    @PostMapping("/save")
    //@PreAuthorize("hasAuthority('developers:write')")
    public UserDto save(@RequestBody UserDto userDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userDto);

        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeUser", "saveUser", message);

        Message resultMessage = rabbitTemplate.receive("queueResult", 5000);

        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }

        String body = new String(resultMessage.getBody());
        UserDto userRes = new ObjectMapper().readValue(body, UserDto.class);

        return userRes;
    }

    @PostMapping("/getById")
    public UserDto getById(@RequestParam Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        UserDto userDto = new UserDto();
        userDto.setId(id);
        String jsonString = objectMapper.writeValueAsString(userDto);
        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeGetUser", "getByIdUser", message);

        Message resultMessage = rabbitTemplate.receive("queueResult", 5000);
        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }
        try {
            UserDto user = objectMapper.readValue(resultMessage.getBody(), UserDto.class);
            return user;
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize result message", e);
        }

    }

    @PostMapping("/deleteById")
    public void deleteById(@RequestParam Long id) {
        rabbitTemplate.convertAndSend("exchangeDeleteUser", "deleteByIdUser", id);
    }

    @PostMapping("/deleteByEntity")
    public void deleteByEntity(@RequestParam Long id) {
        rabbitTemplate.convertAndSend("exchangeDeleteUser", "deleteByEntityUser", id);
    }

    @PostMapping("/deleteAll")
    public void deleteAll() {
        rabbitTemplate.convertAndSend("exchangeDeleteAllUsers", "deleteAllUsers", "");
    }

    @PostMapping("/update")
    public UserDto update(@RequestBody UserDto userDto, @RequestParam Long id) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userDto);

        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeUpdateUser", "updateUser", message);

        Message resultMessage = rabbitTemplate.receive("queueResult", 5000);

        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }

        String body = new String(resultMessage.getBody());
        UserDto userRes = new ObjectMapper().readValue(body, UserDto.class);

        return userRes;
    }

    @PostMapping("/getAll")
    public List<UserDto> getAll() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<UserDto> userDto = new ArrayList<>();
        String jsonString = objectMapper.writeValueAsString(userDto);
        MessageProperties props = new MessageProperties();
        props.setReplyTo("queueGetAllUsersResult");
        Message message = new Message(jsonString.getBytes(), props);

        rabbitTemplate.send("exchangeGetAllUsers", "getAllUsers", message);
        Message resultMessage = rabbitTemplate.receive("queueGetAllUsersResult", 5000);
        if (resultMessage == null) {
            throw new RuntimeException("Failed to receive result message");
        }
        String responseBody = new String(resultMessage.getBody());
        List<UserDto> userRes = new ObjectMapper().readValue(responseBody, new TypeReference<List<UserDto>>() {
        });
        return userRes;

    }
}
