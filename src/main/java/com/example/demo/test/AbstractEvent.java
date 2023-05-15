package com.example.demo.test;

import lombok.Getter;

@Getter
public abstract class AbstractEvent {
    public AbstractEvent(String message) {
        this.message = message;
    }

    public AbstractEvent() {
    }

    private String message;
}
