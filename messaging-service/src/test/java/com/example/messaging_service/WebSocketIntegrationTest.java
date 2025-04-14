package com.example.messaging_service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketIntegrationTest {

    @LocalServerPort
    private int port;

    private WebSocketClient client;

    @BeforeEach
    public void setUp() {
        client = new StandardWebSocketClient();
    }

    @Test
    public void testMessageExchange() throws Exception {
        String wsUrl = "ws://localhost:" + port + "/ws/chat?userId=alice";

        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<String> receivedMessage = new AtomicReference<>();

        client.doHandshake(new TextWebSocketHandler() {
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                receivedMessage.set(message.getPayload());
                latch.countDown();
            }
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                session.sendMessage(new TextMessage("{\"to\": \"alice\", \"message\": \"Hello!\"}"));
            }
        },wsUrl).get();

        boolean completed = latch.await(3, TimeUnit.SECONDS);

        assertTrue(completed, "Message was not recieved in time.");
        assertEquals("Hello!", receivedMessage.get());
    }

}
