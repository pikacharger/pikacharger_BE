package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponseDto{
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;

    public ChatRoomResponseDto(ChatRoom entity) {
        this.id = entity.getId();
        this.createDate = entity.getCreateDate();
        this.lastModifiedDate = entity.getLastModifiedDate();
    }
}