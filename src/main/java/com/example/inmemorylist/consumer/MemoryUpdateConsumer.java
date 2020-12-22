package com.example.inmemorylist.consumer;

import com.example.inmemorylist.model.event.CategoryChangedEvent;
import com.example.inmemorylist.properties.CategoryListProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemoryUpdateConsumer {
    private final Logger log = LoggerFactory.getLogger(MemoryUpdateConsumer.class);

    private final CategoryListProperties categoryListProperties;

    @RabbitListener(queues = "#{rabbitConfigProperties.getEvents().get('couchbase-category-changed-event').getQueue()}")
    public void consumeCategoryChangedEvent(CategoryChangedEvent event) {
        log.info("MemoryUpdateConsumer consumeCategoryChangedEvent was listened: {}", event);
        try {
            if (event.isDeleted()) {
                categoryListProperties.removeCategory(event.getId());
                log.info("Category removed. CategoryId: {}", event.getId());
                return;
            }

            categoryListProperties.setCategory(event.getId(), event.getName());
            log.info("Category updated. CategoryId: {}", event.getId());
        } catch (Exception e) {
            log.error("MemoryUpdateConsumer consumeCategoryChangedEvent listener error: {}, event: {}", e.getMessage(), event);
        }
    }
}
