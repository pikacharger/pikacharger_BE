package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.repository.ChatRoomRepository;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private ChatRoomService chatRoomService;

    @Transactional
    public ChatRoomResponseDto findById(final Long id) {
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        return new ChatRoomResponseDto(entity);
    }

}
