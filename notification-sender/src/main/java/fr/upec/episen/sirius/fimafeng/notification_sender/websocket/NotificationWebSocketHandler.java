package fr.upec.episen.sirius.fimafeng.notification_sender.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.upec.episen.sirius.fimafeng.notification_sender.services.NotificationSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Component
public class NotificationWebSocketHandler extends TextWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationWebSocketHandler.class);

    @Autowired
    private NotificationSessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        LOGGER.info("WebSocket connection established: {}", session.getId());
        // wait for registration message from client
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        try {
            JsonNode node = objectMapper.readTree(payload);
            if (node.has("type") && "register".equals(node.get("type").asText())) {
                int userId = node.get("userId").asInt();
                sessionService.registerSession(session, userId);
                LOGGER.info("Session {} registered for user {}", session.getId(), userId);
                // ack
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of("type", "registered", "sessionId", session.getId()))));
                return;
            }
            LOGGER.debug("Received WS message (unhandled): {}", payload);
        } catch (Exception e) {
            LOGGER.error("Failed to handle WS message: {}", payload, e);
            try { session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of("type", "error", "message", "invalid payload")))); } catch (Exception ignored) {}
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        LOGGER.warn("Transport error on session {}: {}", session.getId(), exception.getMessage());
        sessionService.unregisterSession(session);
        try { session.close(CloseStatus.SERVER_ERROR); } catch (Exception ignored) {}
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        LOGGER.info("WebSocket connection closed: {} status={}", session.getId(), status);
        sessionService.unregisterSession(session);
    }
}
