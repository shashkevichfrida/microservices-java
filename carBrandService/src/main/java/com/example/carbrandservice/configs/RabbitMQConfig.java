package com.example.carbrandservice.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        rabbitTemplate.setExchange("exchangeResult");
        rabbitTemplate.setReplyTimeout(5000);
        return rabbitTemplate;
    }

    @Bean
    public Queue queueUser() {
        return new Queue("queueUser", true);
    }

    @Bean
    public Queue queueGetUser() {
        return new Queue("queueGetUser", true);
    }

    @Bean
    public Queue queueDeleteUser() {
        return new Queue("queueDeleteUser", true);
    }

    @Bean
    public Queue queueDeleteAllUsers() {
        return new Queue("queueDeleteAllUsers", true);
    }

    @Bean
    public Queue queueGetAllUsers() {
        return new Queue("queueGetAllUsers", true);
    }

    @Bean
    public Queue queueGetAllUsersResult() {
        return new Queue("queueGetAllUsersResult", true);
    }

    @Bean
    public DirectExchange exchangeUser() {
        return new DirectExchange("exchangeUser");
    }

    @Bean
    public DirectExchange exchangeGetUser() {
        return new DirectExchange("exchangeGetUser");
    }

    @Bean
    public DirectExchange exchangeDeleteUser() {
        return new DirectExchange("exchangeDeleteUser");
    }

    @Bean
    public DirectExchange exchangeDeleteAllUsers() {
        return new DirectExchange("exchangeDeleteAllUsers");
    }

    @Bean
    public DirectExchange exchangeGetAllUsersResult() {
        return new DirectExchange("exchangeGetAllUsersResult");
    }

    @Bean
    public DirectExchange exchangeGetAllUsers() {
        return new DirectExchange("exchangeGetAllUsers");
    }

    @Bean
    public Binding bindingSaveUser() {
        return BindingBuilder
                .bind(queueUser())
                .to(exchangeUser())
                .with("saveUser");
    }

    @Bean
    public Binding bindingGetUser() {
        return BindingBuilder
                .bind(queueGetUser())
                .to(exchangeGetUser())
                .with("getByIdUser");
    }

    @Bean
    public Binding bindingDeleteUser() {
        return BindingBuilder
                .bind(queueGetUser())
                .to(exchangeGetUser())
                .with("deleteByIdUser");
    }
    @Bean
    public Binding bindingDeleteEntityUser() {
        return BindingBuilder
                .bind(queueGetUser())
                .to(exchangeGetUser())
                .with("deleteByEntityUser");
    }

    @Bean
    public Binding bindingDeleteAllUsers() {
        return BindingBuilder
                .bind(queueDeleteAllUsers())
                .to(exchangeDeleteAllUsers())
                .with("deleteAllUsers");
    }

    @Bean
    public Binding bindingGetAllUsers() {
        return BindingBuilder
                .bind(queueGetAllUsers())
                .to(exchangeGetAllUsers())
                .with("getAllUsers");
    }

    @Bean
    public Queue queueResult() {
        return new Queue("queueResult", true);
    }

    @Bean
    public DirectExchange exchangeResult() {
        return new DirectExchange("exchangeResult");
    }
    @Bean
    public Binding bindingReturnDto() {
        return BindingBuilder
                .bind(queueResult())
                .to(exchangeResult())
                .with("returnDto");
    }

    @Bean
    public Binding bindingReturnGetAllDto() {
        return BindingBuilder
                .bind(queueGetAllUsersResult())
                .to(exchangeGetAllUsersResult())
                .with("getAllUsersResult");
    }

    @Bean
    public Queue queueCarBrand() {
        return new Queue("queueBrand", true);
    }

    @Bean
    public Queue queueGetCarBrand() {
        return new Queue("queueGetBrand", true);
    }

    @Bean
    public Queue queueDeleteCarBrand() {
        return new Queue("queueDeleteCarBrand", true);
    }

    @Bean
    public Queue queueDeleteAllCarBrands() {
        return new Queue("queueDeleteAllCarBrands", true);
    }

    @Bean
    public Queue queueGetAllCarBrands() {
        return new Queue("queueGetAllCarBrands", true);
    }

    @Bean
    public Queue queueGetAllCarBrandsResult() {
        return new Queue("queueGetAllCarBrandsResult", true);
    }

    @Bean
    public DirectExchange exchangeCarBrand() {
        return new DirectExchange("exchangeCarBrand");
    }

    @Bean
    public DirectExchange exchangeGetCarBrand() {
        return new DirectExchange("exchangeGetCarBrand");
    }

    @Bean
    public DirectExchange exchangeDeleteCarBrand() {
        return new DirectExchange("exchangeDeleteCarBrand");
    }

    @Bean
    public DirectExchange exchangeDeleteAllCarBrands() {
        return new DirectExchange("exchangeDeleteAllCarBrands");
    }

    @Bean
    public DirectExchange exchangeGetAllCarBrandsResult() {
        return new DirectExchange("exchangeGetAllCarBrandsResult");
    }

    @Bean
    public DirectExchange exchangeGetAllCarBrands() {
        return new DirectExchange("exchangeGetAllCarBrands");
    }

    @Bean
    public Binding bindingSaveCarBrand() {
        return BindingBuilder
                .bind(queueCarBrand())
                .to(exchangeCarBrand())
                .with("saveCarBrand");
    }

    @Bean
    public Binding bindingGetCarBrand() {
        return BindingBuilder
                .bind(queueGetCarBrand())
                .to(exchangeGetCarBrand())
                .with("getByIdCarBrand");
    }

    @Bean
    public Binding bindingDeleteCarBrand() {
        return BindingBuilder
                .bind(queueDeleteCarBrand())
                .to(exchangeDeleteCarBrand())
                .with("deleteByIdCarBrand");
    }
    @Bean
    public Binding bindingDeleteEntityCarBrand() {
        return BindingBuilder
                .bind(queueDeleteCarBrand())
                .to(exchangeDeleteCarBrand())
                .with("deleteByEntityCarBrand");
    }

    @Bean
    public Binding bindingDeleteAllCarBrands() {
        return BindingBuilder
                .bind(queueDeleteAllCarBrands())
                .to(exchangeDeleteAllCarBrands())
                .with("deleteAllCarBrands");
    }

    @Bean
    public Binding bindingGetAllCarBrands() {
        return BindingBuilder
                .bind(queueGetAllCarBrands())
                .to(exchangeGetAllCarBrands())
                .with("getAllCarBrands");
    }

    @Bean
    public Queue queueCarBrandResult() {
        return new Queue("queueCarBrandResult", true);
    }

    @Bean
    public DirectExchange exchangeCarBrandResult() {
        return new DirectExchange("exchangeCarBrandResult");
    }

    @Bean
    public Binding bindingUpdateUser() {
        return BindingBuilder
                .bind(updateCarBrand())
                .to(exchangeUpdateCarBrand())
                .with("updateCarBrand");
    }

    @Bean
    public DirectExchange exchangeUpdateCarBrand() {
        return new DirectExchange("updateCarBrand");
    }

    @Bean
    public Queue updateCarBrand() {
        return new Queue("queueUpdateCarBrand", true);
    }

}

