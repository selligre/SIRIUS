package fr.upec.episen.sirius.fimafeng.notification_sender.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.upec.episen.sirius.fimafeng.commons.models.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationSessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationSessionService.class);

    private final Map<String, WebSocketSession> sessionsById = new ConcurrentHashMap<>();
    private final Map<Integer, Set<String>> sessionsByUser = new ConcurrentHashMap<>();
    private final Map<String, Integer> sessionToUser = new ConcurrentHashMap<>();

    private final ObjectMapper objectMapper;

    @Autowired
    public NotificationSessionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void registerSession(WebSocketSession session, int userId) {
        if (session == null) return;
        sessionsById.put(session.getId(), session);
        sessionToUser.put(session.getId(), userId);
        sessionsByUser.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet()).add(session.getId());
        LOGGER.info("Registered WS session {} for user {}", session.getId(), userId);
    }

    public void unregisterSession(WebSocketSession session) {
        if (session == null) return;
        String sid = session.getId();
        sessionsById.remove(sid);
        Integer uid = sessionToUser.remove(sid);
        if (uid != null) {
            Set<String> set = sessionsByUser.get(uid);
            if (set != null) {
                set.remove(sid);
                if (set.isEmpty()) {
                    sessionsByUser.remove(uid);
                }
            }
        }
        LOGGER.info("Unregistered WS session {}", sid);
    }

    private void unregisterSessionForId(String sid) {
        if (sid == null) return;
        sessionsById.remove(sid);
        Integer uid = sessionToUser.remove(sid);
        if (uid != null) {
            Set<String> set = sessionsByUser.get(uid);
            if (set != null) {
                set.remove(sid);
                if (set.isEmpty()) sessionsByUser.remove(uid);
            }
        }
    }

    public void sendNotificationToUser(NotificationEvent event) {
        if (event == null) return;
        Set<String> targetSessionIds = sessionsByUser.get(event.getUserId());
        if (targetSessionIds == null || targetSessionIds.isEmpty()) {
            LOGGER.info("No active WS sessions for user {}", event.getUserId());
            return;
        }

        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            LOGGER.error("Error serializing notification event", e);
            return;
        }

        TextMessage message = new TextMessage(payload);

        for (String sid : targetSessionIds.toArray(new String[0])) {
            WebSocketSession session = sessionsById.get(sid);
            if (session == null || !session.isOpen()) {
                LOGGER.info("Removing closed or null session {}", sid);
                unregisterSessionForId(sid);
                continue;
            }
            try {
                session.sendMessage(message);
            } catch (Exception e) {
                LOGGER.error("Error sending message to session {}: {}", sid, e.getMessage());
                try { session.close(); } catch (Exception ignored) {}
                unregisterSessionForId(sid);
            }
        }
    }
}
