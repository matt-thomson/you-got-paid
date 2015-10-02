package com.gocardless.ws;

import com.google.common.collect.Sets;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

public class BroadcastSocket extends WebSocketAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BroadcastSocket.class);
    private static final Set<Session> sessions = Sets.newCopyOnWriteArraySet();

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);

        sessions.add(session);
        LOGGER.info("Socket connected: [{}]", session.getRemoteAddress());
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        super.onWebSocketClose(statusCode, reason);

        Session session = getSession();

        sessions.remove(session);
        LOGGER.info("Socket closed: [{}] (reason: [{}])", session.getLocalAddress(), reason);
    }

    @Override
    public void onWebSocketError(Throwable cause) {
        super.onWebSocketError(cause);

        LOGGER.error("Socket error", cause);
    }

    public static void broadcast(String json) {
        sessions.forEach(session -> {
            try {
                session.getRemote().sendString(json);
            } catch (IOException e) {
                LOGGER.error("Error broadcasting message", e);
            }
        });
    }
}
