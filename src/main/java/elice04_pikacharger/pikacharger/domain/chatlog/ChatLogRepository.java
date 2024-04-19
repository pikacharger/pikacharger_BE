package elice04_pikacharger.pikacharger.domain.chatlog;

import elice04_pikacharger.pikacharger.domain.chatroom.ChatRoom;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatLogRepository {
    private final EntityManager manager;
    public List<ChatLog> getAllChatLog(ChatRoom chatRoom) {
        return manager.createQuery("select chat from ChatLog chat where chat.chatRoom = :chatRoom", ChatLog.class)
                .setParameter("chatRoom", chatRoom)
                .getResultList();
    }

    public Long createChatlog(ChatLog chatLog) {
        manager.persist(chatLog);
        return chatLog.getId();
    }
}
