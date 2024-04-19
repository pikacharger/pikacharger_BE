package elice04_pikacharger.pikacharger.domain.chatroom;

import elice04_pikacharger.pikacharger.domain.websocket.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="chatroom")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    // 수정필요

    // 채팅은 삭제x 채팅방은 삭제가능하게하는데, 유저 삭제를 원하는 유저한테서만 삭제되고, 원하지 않던 상대방은 남아있어야함
    // ->가능하면 수정하고 FE에 말해주기
}
