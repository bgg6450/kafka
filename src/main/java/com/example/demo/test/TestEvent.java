package com.example.demo.test;

import lombok.*;

@Getter
@ToString
public class TestEvent extends AbstractEvent {
//    private String message;
    public TestEvent(String s) {
        super(s);
    }

    public TestEvent() {
        super();
    }
}
