package com.example.inmemorylist.properties.rabbit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rabbit.retry-policy")
public class MqRabbitRetryProperties {
    private int maxAttempt;
    private long initialInterval;
    private double multiplier;
    private long maxInterval;
}