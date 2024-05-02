package elice04_pikacharger.pikacharger.domain.chat.entity;

import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name="chatlog")
@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ChatLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatlog_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id", insertable = false, updatable = false)
    private ChatRoom chatRoom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User sender;

    private String messageContents;
}