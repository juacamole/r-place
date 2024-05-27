package com.example.backend.websocket;

import com.example.backend.*;
import com.example.backend.jwt.config.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class WebSocketHandler implements org.springframework.web.socket.WebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final BackendService service;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public WebSocketHandler(BackendService service, JWTService jwtService, UserDetailsService userDetailsService, UserService userService) {
        this.service = service;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Message decodedMessage = mapper.readValue(message.getPayload().toString(), Message.class);
        String username = jwtService.extractUsername(decodedMessage.getToken());
        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(decodedMessage.getToken(), userDetails) && decodedMessage.getToken() != null && !decodedMessage.getCanvas().isEmpty()) {
                Optional<UserData> user = userService.getUser(decodedMessage.getToken());
                if (user.isPresent()) {
                    if (user.get().getRole() == Role.ADMIN) {
                        CanvasData newestCanvas = service.updateCanvas(new CanvasData(decodedMessage.getCanvas()));
                        service.addPixel(decodedMessage.getToken());
                        sendCanvasToAll(newestCanvas);
                    } else {
                        long cooldownInMiliseconds = 14000;
                        if (userService.getCooldownByUser(decodedMessage.getToken()) + cooldownInMiliseconds <= System.currentTimeMillis()) {
                            CanvasData newestCanvas = service.updateCanvas(new CanvasData(decodedMessage.getCanvas()));
                            service.addPixel(decodedMessage.getToken());
                            userService.refreshCooldown(decodedMessage.getToken());
                            sendCanvasToAll(newestCanvas);
                        } else {
                            sendCanvasToAll(service.getCanvas());
                        }
                    }
                } else sendCanvasToAll(service.getCanvas());
            } else sendCanvasToAll(service.getCanvas());
        }
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) {

    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) {

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
