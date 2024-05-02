package elice04_pikacharger.pikacharger.websocket;

//import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogDto;

import com.fasterxml.jackson.databind.ObjectMapper;
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
//    private final ObjectMapper mapper;
//    private final Set<WebSocketSession> sessions = new HashSet<>();
//    private final Map<Long,Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();
//
//    // 소켓 연결 확인하기
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("{} 연결되었습니다.", session.getId());
//        sessions.add(session);
//    }
//
//    // 메시지 전송 부분
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        log.info("payload {}", payload);
//
//        ChatLogDto chatLogDto = mapper.readValue(payload, ChatLogDto.class);
//        log.info("session {}", chatLogDto.toString());
//
//        Long chatRoomId = chatLogDto.getChatRoomId();
//        if(!chatRoomSessionMap.containsKey(chatRoomId)){
//            chatRoomSessionMap.put(chatRoomId,new HashSet<>());
//        }
//        Set<WebSocketSession> chatRoomSession = chatRoomSessionMap.get(chatRoomId);
//
//        if (chatLogDto.getMessageType().equals(ChatLogDto.MessageType.ENTER)) {
//            chatRoomSession.add(session);
//        }
//        if (chatRoomSession.size()>=3) {
//            removeClosedSession(chatRoomSession);
//        }
//        sendLogToChatRoom(chatLogDto, chatRoomSession);
//    }
//
//    // 소켓 종료 확인하기
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info("{} 연결이 끊겼습니다.", session.getId());
//        sessions.remove(session);
//    }
//
//    private void removeClosedSession(Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.removeIf(sess -> !sessions.contains(sess));
//    }
//
//    private void sendLogToChatRoom(ChatLogDto chatLogDto, Set<WebSocketSession> chatRoomSession) {
//        chatRoomSession.parallelStream().forEach(sess -> sendMessage(sess, chatLogDto));//2
//    }
//
//    public <T> void sendMessage(WebSocketSession session, T message) {
//        try{
//            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
}