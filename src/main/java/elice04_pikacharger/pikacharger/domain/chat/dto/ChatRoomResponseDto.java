package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class ChatRoomResponseDto {
    private Long chargerId;
}