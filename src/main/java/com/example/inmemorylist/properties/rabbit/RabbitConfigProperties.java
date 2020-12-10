package com.example.inmemorylist.properties.rabbit;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "rabbit")
public class RabbitConfigProperties {
    private Map<String, EventProperties> events = new HashMap<>();
}