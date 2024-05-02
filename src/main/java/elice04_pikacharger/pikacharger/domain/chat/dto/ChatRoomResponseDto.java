package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
@AllArgsConstructor
public class ChatRoomResponseDto extends BaseEntity{
//    private Long id;
//    private String createdDate;
//    private String lastModifiedDate;

//    public ChatRoomResponseDto(ChatRoom entity) {
//        this.id = entity.getId();
//        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//        this.lastModifiedDate = entity.getlastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
//    }
}