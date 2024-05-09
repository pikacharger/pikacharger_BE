package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomRequestDto {
    private Long id;
    private Charger charger;

    public void setCharger(Charger charger) {
        this.charger = charger;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .id(this.id)
                .charger(this.charger)
                .build();
    }
}