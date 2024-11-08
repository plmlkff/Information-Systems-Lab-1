package ru.itmo.is_lab1.rest.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/api/domain/changed")
public class NotificationWebSocket {
    private static final Set<Session> activeSessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        activeSessions.add(session);
        System.out.println("New connection: " + session.getId());
    }

    public static void notifyAllListeners(String message){
        for (var session : activeSessions){
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException ignored) {
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        activeSessions.remove(session);
        System.out.println("Session closed: " + session.getId());
    }
}
