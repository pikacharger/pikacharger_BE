package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomRequestDto {
    private Long id;

    @Builder
    public ChatRoomRequestDto(Long roomId) {
        this.id = roomId;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .id(this.id)
                .build();
    }
}
