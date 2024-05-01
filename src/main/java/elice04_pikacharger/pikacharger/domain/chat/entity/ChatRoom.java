package elice04_pikacharger.pikacharger.domain.chat.entity;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="chatroom")
@Entity
@NoArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Builder
    public ChatRoom(Long id) {
        this.id = id;
    }
}
