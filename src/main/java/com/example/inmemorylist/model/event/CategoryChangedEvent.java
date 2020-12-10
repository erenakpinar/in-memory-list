package com.example.inmemorylist.model.event;

import lombok.Data;

@Data
public class CategoryChangedEvent {
    private long id;
    private String name;
    private boolean deleted;
}
