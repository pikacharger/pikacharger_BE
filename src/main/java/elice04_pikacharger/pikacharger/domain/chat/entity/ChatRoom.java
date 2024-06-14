package elice04_pikacharger.pikacharger.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.common.global.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name="chatroom")
@Entity
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "charger_id")
    @JsonBackReference
    private Charger charger;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonBackReference
    private User receiverId;

    public ChatLog getLastChatLog() {
        if (chatLogs != null && !chatLogs.isEmpty()) {
            // chatLogs 리스트가 비어있지 않은 경우에만 마지막 채팅 로그를 가져옴
            return chatLogs.get(chatLogs.size() - 1);
        } else {
            return createDefaultChatLog();
        }
    }

    private ChatLog createDefaultChatLog() {
        ChatLog defaultChatLog = new ChatLog();
        defaultChatLog.setMessageContents("대화를 기다리고 있어요. 무엇이든 물어보세요!");
        return defaultChatLog;
    }

    @Builder
    public ChatRoom(User user,Charger charger, User receiverId){
        this.user = user;
        this.charger = charger;
        this.receiverId = receiverId;
    }
}