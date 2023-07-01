package com.example.carbrandservice.listeners;


import com.example.carbrandservice.services.impl.CarBrandServiceImpl;
import com.example.interfaces.dto.CarBrandDto;
import com.example.interfaces.dto.CarBrandUserDto;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@EnableRabbit
@Component
@RabbitListener
@AllArgsConstructor
public class CarBrandListener {

    private final CarBrandServiceImpl carBrandServices;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueCarBrand", durable = "true"),
            exchange = @Exchange(value = "exchangeCarBrand", type = ExchangeTypes.DIRECT),
            key = {"saveCarBrand"}))
    public CarBrandDto save(CarBrandUserDto carBrandUserDto){

        CarBrandDto carBrand = carBrandServices.save(carBrandUserDto);
        carBrandUserDto.setCarBrandDto(carBrand);
        rabbitTemplate.convertAndSend( "exchangeCarBrandResult", "returnCarBrandDto", carBrand);
        return carBrand;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueGetAllCarBrands", durable = "true"),
            exchange = @Exchange(value = "exchangeGetAllCarBrands", type = ExchangeTypes.DIRECT),
            key = {"getAllCarBrands"}))
    public List<CarBrandDto> getAll(String userName){
        List<CarBrandDto> brands = carBrandServices.getAll(userName);
        rabbitTemplate.convertAndSend( "exchangeCarBrandResult", "returnCarBrandDto", brands);
        return brands;

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueGetBrand", durable = "true"),
            exchange = @Exchange(value = "exchangeGetCarBrand", type = ExchangeTypes.DIRECT),
            key = {"getByIdCarBrand"}))
    public CarBrandDto getById(CarBrandUserDto carBrandDto){
        CarBrandDto carBrand = carBrandServices.getById(carBrandDto.getCarBrandDto().getId(), carBrandDto.getUserName());
        rabbitTemplate.convertAndSend( "exchangeCarBrandResult", "returnCarBrandDto", carBrand);
        return carBrand;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueUpdateCarBrand", durable = "true"),
            exchange = @Exchange(value = "exchangeUpdateCarBrand", type = ExchangeTypes.DIRECT),
            key = {"updateCarBrand"}))
    public void update(CarBrandUserDto carBrandDto) {
        Long id = carBrandDto.getCarBrandDto().getId();
        CarBrandDto car = carBrandServices.update(carBrandDto.getCarBrandDto(), id, carBrandDto.getUserName());
        rabbitTemplate.convertAndSend( "exchangeCarBrandResult", "returnCarBrandDto", car);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteCarBrand", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteCarBrand", type = ExchangeTypes.DIRECT),
            key = {"deleteByEntityCarBrand"}))
    public void deleteByEntity(CarBrandUserDto carBrandUserDto){
        carBrandServices.deleteByEntity(carBrandUserDto.getCarBrandDto().getId(), carBrandUserDto.getUserName());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteCarBrand", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteCarBrand", type = ExchangeTypes.DIRECT),
            key = {"deleteByIdCarBrand"}))
    public void deleteById(CarBrandUserDto carBrandUserDto){
        carBrandServices.deleteById(carBrandUserDto.getCarBrandDto().getId(), carBrandUserDto.getUserName());
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteAllCarBrands", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteAllCarBrands", type = ExchangeTypes.DIRECT),
            key = {"deleteAllCarBrands"}))
    public void deleteAll(String userName){
        carBrandServices.deleteAll(userName);
    }
}
