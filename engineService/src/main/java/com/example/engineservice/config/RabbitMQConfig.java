package com.example.engineservice.config;

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
    public Queue updateUser() {
        return new Queue("queueUpdateUser", true);
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
    public DirectExchange exchangeUpdateUser() {
        return new DirectExchange("updateUser");
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
    public Binding bindingUpdateUser() {
        return BindingBuilder
                .bind(updateUser())
                .to(exchangeUpdateUser())
                .with("updateUser");
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
}

