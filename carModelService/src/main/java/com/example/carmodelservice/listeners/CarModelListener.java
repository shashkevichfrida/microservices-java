package com.example.carmodelservice.listeners;

import com.example.carmodelservice.services.impl.CarModelServiceImpl;
import com.example.interfaces.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@EnableRabbit
@Component
@RabbitListener
@AllArgsConstructor
public class CarModelListener {


    private final CarModelServiceImpl carModelService;

    private final RabbitTemplate rabbitTemplate;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueCarModel", durable = "true"),
            exchange = @Exchange(value = "exchangeCarModel", type = ExchangeTypes.DIRECT),
            key = {"saveCarModel"}))
    public CarModelDto save(CarModelUserDto carModelUserDto){

        CarModelDto carModel = carModelService.save(carModelUserDto.getCarModelDto(), carModelUserDto.getUserName());
        //carModelUserDto.setCarBrandDto(carBrand);
        rabbitTemplate.convertAndSend( "exchangeCarBrandResult", "returnCarBrandDto", carModel);
        return carModel;
    }

    /*@PostMapping("/getAll")
    public List<CarBrandDto> getAll(){
        return carBrandServices.getAll();
    }*/

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueGetCarModel", durable = "true"),
            exchange = @Exchange(value = "exchangeGetCarModel", type = ExchangeTypes.DIRECT),
            key = {"getByIdCarModel"}))
    public CarModelDto getById(CarModelUserDto carModelDto){
        CarModelDto model = carModelService.getById(carModelDto.getCarModelDto().getId(), carModelDto.getUserName());
        rabbitTemplate.convertAndSend( "exchangeCarModelResult", "returnCarModelDto", model);
        return model;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueUpdateCarModel", durable = "true"),
            exchange = @Exchange(value = "exchangeUpdateCarModel", type = ExchangeTypes.DIRECT),
            key = {"updateCarModel"}))
    public void update(CarModelUserDto carModelDto) {
        Long id = carModelDto.getCarModelDto().getId();
        //carBrandDto.getCarBrandDto().setId(null);
        CarModelDto model = carModelService.update(carModelDto.getCarModelDto(), id, carModelDto.getUserName());
        rabbitTemplate.convertAndSend( "exchangeCarModelResult", "returnCarModelDto", model);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteCarModel", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteCarModel", type = ExchangeTypes.DIRECT),
            key = {"deleteByEntityCarModel"}))
    public void deleteByEntity(Long id){
        carModelService.deleteByEntity(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteCarModel", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteCarModel", type = ExchangeTypes.DIRECT),
            key = {"deleteByIdCarModel"}))
    public void deleteById(Long id){
        carModelService.deleteById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queueDeleteAllCarModels", durable = "true"),
            exchange = @Exchange(value = "exchangeDeleteAllCarModels", type = ExchangeTypes.DIRECT),
            key = {"deleteAllCarModels"}))
    public void deleteAll(){
        carModelService.deleteAll();
    }
}
