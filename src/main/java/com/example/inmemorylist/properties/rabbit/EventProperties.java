package com.example.inmemorylist.properties.rabbit;

import lombok.Data;

import java.util.UUID;

@Data
public class EventProperties {
    private String exchange;
    private String queue;
    private String routingKey = "";

    public void setQueue(String queueName) {
        this.queue = queueName + "." + UUID.randomUUID().toString();
    }
}
