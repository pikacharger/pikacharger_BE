package elice04_pikacharger.pikacharger.websocket;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;
    private final Set<WebSocketSession> sessions = new HashSet<>();
    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인하기
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String uri = session.getUri().toString();
        Long chatRoomId = extractChatRoomId(uri);
        if (chatRoomId == null) {
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        log.info("{} 연결되었습니다.", session.getId(), chatRoomId);
        System.out.printf("%s 연결되었습니다.\n", session.getId(), chatRoomId);
        sessions.add(session);

        chatRoomSessionMap.computeIfAbsent(chatRoomId, k -> new HashSet<>()).add(session);
    }

    // 클라이언트로부터 수신된 메시지를 textMessage로 받아서 처리하고 변환
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);

        ChatLogResponseDto chatLogResponseDto = mapper.readValue(payload, ChatLogResponseDto.class);
        log.info("session {}", chatLogResponseDto.toString());

        Long chatRoomId = chatLogResponseDto.getChatRoomId();

        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);
        if (chatRoomSession == null) {
            chatRoomSession = new HashSet<>();
            chatRoomSessionMap.put(chatRoomId, chatRoomSession);
        }

        removeClosedSession(chatRoomSession);

        sendLogToChatRoom(chatLogResponseDto, chatRoomSession);
    }

// 소켓 종료 확인하기
    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        log.info("{} 연결이 끊겼습니다.", session.getId());
        System.out.printf("%s 연결이 끊겼습니다.\n", session.getId());
        sessions.remove(session);

        chatRoomSessionMap.values().forEach(chatRoomSession -> chatRoomSession.remove(session));
    }

    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
    }

    private void sendLogToChatRoom(ChatLogResponseDto chatLogResponseDto, Set<WebSocketSession> chatRoomSession) {
        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatLogResponseDto));
    }

    // 메시지 전송
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    // URI에서 chatRoomId 추출
    private Long extractChatRoomId(String uri) {
        try {
            String[] parts = uri.split("/");
            String chatRoomIdStr = parts[parts.length - 1];
            return Long.parseLong(chatRoomIdStr);
        } catch (Exception e) {
            log.error("Failed to extract chatRoomId from URI: {}", uri, e);
            return null;
        }
    }
}