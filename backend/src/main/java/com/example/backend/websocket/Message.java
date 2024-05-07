package com.example.backend.websocket;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class Message {
    private String token;
    private String canvas;
}
