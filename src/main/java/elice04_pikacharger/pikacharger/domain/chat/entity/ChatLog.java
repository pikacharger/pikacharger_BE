package elice04_pikacharger.pikacharger.domain.chat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Table(name="chatlog")
@Entity
@Data
@Getter
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChatLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatlog_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatroom_id", updatable = false)
    @JsonBackReference
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    @JsonBackReference
    private User sender;

    private String messageContents;

    public User getSender() {
        return this.sender;
    }

    public String getMessageContents() {
        return this.messageContents;
    }

    public void setMessageContents(String message) {
        this.messageContents = message;
    }

    @Builder
    public ChatLog(ChatRoom chatRoom, User sender, String messageContents) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.messageContents = messageContents;
    }
}