package com.example.inmemorylist.config.rabbit;

import com.example.inmemorylist.properties.rabbit.EventProperties;
import com.example.inmemorylist.properties.rabbit.MqRabbitRetryProperties;
import com.example.inmemorylist.properties.rabbit.RabbitConfigProperties;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import java.util.HashMap;
import java.util.stream.Collectors;

@Configuration
public class RabbitConfiguration {

    @Autowired
    private MqRabbitRetryProperties mqRabbitRetryProperties;

    @Autowired
    private RabbitConfigProperties rabbitConfigProperties;

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Primary
    public RabbitTemplate rabbitTemplate(RabbitAdmin rabbitAdmin, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public RetryOperationsInterceptor mqRetryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(mqRabbitRetryProperties.getMaxAttempt())
                .backOffOptions(mqRabbitRetryProperties.getInitialInterval(),
                        mqRabbitRetryProperties.getMultiplier(),
                        mqRabbitRetryProperties.getMaxInterval())
                .build();
    }

    @Bean
    public Declarables rabbitQueues(RabbitAdmin rabbitAdmin) {
        return new Declarables(
                rabbitConfigProperties.getEvents().values().stream()
                        .map(event -> createQueue(event, rabbitAdmin))
                        .collect(Collectors.toList())
        );
    }

    @Bean
    public Declarables rabbitBindings() {
        return new Declarables(
                rabbitConfigProperties.getEvents().values().stream()
                        .map(this::createQueueBinding)
                        .collect(Collectors.toList())
        );
    }

    @Bean
    public Declarables rabbitExchanges(RabbitAdmin rabbitAdmin) {
        return new Declarables(
                rabbitConfigProperties.getEvents().values().stream()
                        .map(event -> createTopicExchange(event, rabbitAdmin))
                        .collect(Collectors.toList())
        );
    }

    private TopicExchange createTopicExchange(EventProperties properties, RabbitAdmin rabbitAdmin) {
        TopicExchange exchange = new TopicExchange(properties.getExchange());
        exchange.setAdminsThatShouldDeclare(rabbitAdmin);

        return exchange;
    }

    private Queue createQueue(EventProperties properties, RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable(properties.getQueue()).autoDelete().build();
        queue.setAdminsThatShouldDeclare(rabbitAdmin);

        return queue;
    }

    private Binding createQueueBinding(EventProperties properties) {
        return new Binding(
                properties.getQueue(),
                Binding.DestinationType.QUEUE,
                properties.getExchange(),
                properties.getRoutingKey(),
                new HashMap<>()
        );
    }
}
