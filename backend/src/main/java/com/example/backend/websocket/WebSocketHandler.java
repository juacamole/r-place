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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
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

    private final Object sendLock = new Object();  // Add this line to the class

    public WebSocketHandler(BackendService service, JWTService jwtService, UserDetailsService userDetailsService, UserService userService) {
        this.service = service;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    private static BufferedImage decodeToImage(String base64Image) throws Exception {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bis);
    }

    private static int compareImages(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            throw new IllegalArgumentException("Bilder müssen die gleiche Größe haben");
        }

        int width = img1.getWidth();
        int height = img1.getHeight();
        int differingPixels = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel1 = img1.getRGB(x, y);
                int pixel2 = img2.getRGB(x, y);
                if (pixel1 != pixel2) {
                    differingPixels++;
                }
            }
        }

        return differingPixels;
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

                            try {
                                byte[] imageBytes = Base64.getDecoder().decode(decodedMessage.getCanvas().substring(22));
                                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                                BufferedImage image = ImageIO.read(bis);
                                bis.close();
                                int width = image.getWidth();
                                int height = image.getHeight();
                                if (width == 400 && height == 400) {

                                    try {
                                        BufferedImage image1 = decodeToImage(service.getCanvas().getCanvasData().substring(22));
                                        BufferedImage image2 = decodeToImage(decodedMessage.getCanvas().substring(22));

                                        int differingPixels = compareImages(image1, image2);

                                        if (differingPixels <= 100) {
                                            System.out.println("Saving Canvas in DB");
                                            CanvasData newestCanvas = service.updateCanvas(new CanvasData(decodedMessage.getCanvas()));
                                            service.addPixel(decodedMessage.getToken());
                                            userService.refreshCooldown(decodedMessage.getToken());
                                            sendCanvasToAll(newestCanvas);
                                        } else {
                                            System.out.println("Input Image has more changes than allowed");
                                            sendCanvasToAll(service.getCanvas());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {
                                    System.out.println("Input Image does not meet criteria");
                                    sendCanvasToAll(service.getCanvas());
                                }
                            } catch (Error error) {
                                System.out.println(error.getMessage());
                            }
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
        synchronized (sendLock) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(textMessage);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }
}
