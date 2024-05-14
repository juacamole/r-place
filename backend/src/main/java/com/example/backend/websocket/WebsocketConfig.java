package com.example.backend.websocket;

import com.example.backend.BackendService;
import com.example.backend.UserService;
import com.example.backend.jwt.config.JWTService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    private final BackendService service;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    public WebsocketConfig(BackendService service, JWTService jwtService, UserDetailsService userDetailsService, UserService userService) {
        this.service = service;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(service, jwtService, userDetailsService, userService), "/ws")
                .setAllowedOrigins("*");
    }

    public WebSocketHandler myHandler() {
        return new WebSocketHandler(service, jwtService, userDetailsService, userService);
    }

}


