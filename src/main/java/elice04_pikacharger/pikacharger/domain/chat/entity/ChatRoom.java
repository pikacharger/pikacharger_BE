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
    // 채팅은 삭제x 채팅방은 삭제가능하게하는데, 유저 삭제를 원하는 유저한테서만 삭제되고, 원하지 않던 상대방은 남아있어야함
    // ->가능하면 수정하고 FE에 말해주기
}
