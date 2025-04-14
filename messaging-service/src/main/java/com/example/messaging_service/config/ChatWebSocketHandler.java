package com.example.messaging_service.config;

import com.example.messaging_service.dto.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserId(session);
        sessions.put(userId, session);
        System.out.println("User connected: " + userId);
    }

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ChatMessage chatMessage = mapper.readValue(message.getPayload(), ChatMessage.class);
        WebSocketSession recieverSession = sessions.get(chatMessage.getTo());
        if (recieverSession != null && recieverSession.isOpen()) {
            recieverSession.sendMessage(new TextMessage(chatMessage.getMessage()));
        }else {
            System.out.println("User " + chatMessage.getTo() + " is offline. Store message in DB");
        }
    }

    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }

    private String getUserId(WebSocketSession session) {
        return  session.getUri().getQuery().split("=")[1];   // extract from query param
    }
}
