package com.example.backend.websocket;

import com.example.backend.BackendService;
import com.example.backend.CanvasData;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler implements org.springframework.web.socket.WebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final BackendService service;

    public WebSocketHandler(BackendService service) {
        this.service = service;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        CanvasData newestCanvas = service.updateCanvas(new CanvasData(message.getPayload().toString()));
        sendCanvasToAll(newestCanvas);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendCanvasToAll(CanvasData canvas) {
        TextMessage textMessage = new TextMessage(canvas.getCanvasData());
        for (WebSocketSession session : sessions) {
            try {
                System.out.println(sessions.toArray().length);
                System.out.println(session);
                session.sendMessage(textMessage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
