package elice04_pikacharger.pikacharger.domain.chat.entity;

//import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="chatroom")
@Entity
@Data
@Builder
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatLog> chatLogs;

    public List<ChatLog> getChatLogs() {
        return this.chatLogs;
    }

    //TODO: 마지막 메시지 조회
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "last_chatlog_id")
//    private ChatLog getLastChatLog;
//
//    public void updateLastChatLog(ChatLog chatLog) {
//        this.lastChatLog = chatLog;
//    }
//
//    @Builder
//    public ChatRoom(Long id, ChatLog lastChatLog) {
//        this.id = id;
//        this.lastChatLog = lastChatLog;
//    }
//
//    public Long update(ChatRoomRequestDto requestDto) {
//        this.lastChatLog = lastChatLog;
//        return this.id;
//    }
}