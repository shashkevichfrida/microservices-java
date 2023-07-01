package com.example.userservice.listeners;

import com.example.interfaces.dto.UserDto;
import com.example.userservice.services.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
@RabbitListener
@AllArgsConstructor
public class UserListener {

    private final UserServiceImpl userService;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueUser", durable = "true"),
            exchange = @Exchange(value = "exchangeUser", type = ExchangeTypes.DIRECT),
    key = {"saveUser"}))
    public UserDto save(UserDto userDto){

        UserDto user = userService.save(userDto);
        rabbitTemplate.convertAndSend( "exchangeResult", "returnDto", user);
        return user;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueGetUser", durable = "true"),
            exchange = @Exchange(value = "exchangeGetUser", type = ExchangeTypes.DIRECT),
            key = {"getByIdUser"}))
    public void getById(UserDto userDto){
        UserDto user = userService.getById(userDto.getId());
        rabbitTemplate.convertAndSend( "exchangeResult", "returnDto", user);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteUser", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteUser", type = ExchangeTypes.DIRECT),
            key = {"deleteByIdUser"}))
    public void deleteById(Long id){
        userService.deleteById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteUser", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteUser", type = ExchangeTypes.DIRECT),
            key = {"deleteByEntityUser"}))
    public void deleteByEntity(Long id){
        userService.deleteByEntity(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteAllUsers", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteAllUsers", type = ExchangeTypes.DIRECT),
            key = {"deleteAllUsers"}))
    public void deleteAll(){
        userService.deleteAll();
    }

/*    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueGetAllUsers", durable = "true"),
            exchange = @Exchange(value = "exchangeGetAllUsers", type = ExchangeTypes.DIRECT),
            key = {"getAllUsers"}))
    public void getAll() throws JsonProcessingException {
        List<UserDto> users = userService.getAll();
        String responseBody = new ObjectMapper().writeValueAsString(users);
        rabbitTemplate.convertAndSend( "exchangeGetAllUsersResult", "GetAllUsersResult", responseBody);
    }*/

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueUpdateUser", durable = "true"),
            exchange = @Exchange(value = "exchangeUpdateUser", type = ExchangeTypes.DIRECT),
            key = {"updateUser"}))
    public void update(UserDto userDto) {
        Long id = userDto.getId();
        userDto.setId(null);
        UserDto user = userService.update(userDto, id);
        rabbitTemplate.convertAndSend( "exchangeResult", "returnDto", user);
    }


}
