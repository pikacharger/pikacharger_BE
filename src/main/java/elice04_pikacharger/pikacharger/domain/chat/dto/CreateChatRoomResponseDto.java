package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateChatRoomResponseDto {
    private Long chatRoomId;

    public static CreateChatRoomResponseDto toDto(ChatRoom chatRoom) {
        return CreateChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                .build();
    }

    @Builder
    public CreateChatRoomResponseDto(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }
}
