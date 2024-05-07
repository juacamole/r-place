package com.example.backend.websocket;

import com.example.backend.BackendService;
import com.example.backend.CanvasData;
import com.example.backend.jwt.config.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public WebSocketHandler(BackendService service, JWTService jwtService, UserDetailsService userDetailsService) {
        this.service = service;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message decodedMessage = mapper.readValue(message.getPayload().toString(), Message.class);
        String username = jwtService.extractUsername(decodedMessage.getToken());
        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(decodedMessage.getToken(), userDetails) && decodedMessage.getToken() != null) {
                CanvasData newestCanvas = service.updateCanvas(new CanvasData(decodedMessage.getCanvas()));
                sendCanvasToAll(newestCanvas);
                System.out.println("token vorhanden");
            } else if (decodedMessage.getToken() == null) {
                CanvasData newestCanvas = service.getCanvas();
                System.out.println(newestCanvas);
                sendCanvasToAll(newestCanvas);
            }
        }
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
                session.sendMessage(textMessage);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
