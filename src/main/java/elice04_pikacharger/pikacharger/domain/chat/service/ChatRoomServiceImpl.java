package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.repository.ChatRoomRepository;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // 채팅방 조회
    @Override
    @Transactional
    public ChatRoomResponseDto findByRoomId(final Long id) {
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        return new ChatRoomResponseDto(entity);
    }

    // 전체 채팅방 조회
    @Override
    @Transactional
    public List<ChatRoomResponseDto> findAllChatRoom(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        List<ChatRoom> chatRooms = chatRoomRepository.findAllChatRoom(user, user);

        List<ChatRoomResponseDto> chatRoomResponseDto = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            chatRoomResponseDto.add(new ChatRoomResponseDto(chatRoom));
        }
        return chatRoomResponseDto;
    }

    // 채팅방 생성
    @Override
    @Transactional
    public Long save(final ChatRoomRequestDto requestDto) {
        return this.chatRoomRepository.save(requestDto.toEntity()).getId();
    }

    //TODO: 마지막 메시지 조회
//    @Override
//    @Transactional
//    public ChatLog getLastChatLog(Long id) {
//        ChatRoom chatRoom = chatRoomRepository.findByRoomId(id);
//        if (chatRoom == null) {
//            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
//        }
//        return chatRoom.getLastChatLog();
//    }

    // 마지막 메시지를 채팅방에서 업데이트
//    @Override
//    @Transactional
//    public Long update(final Long id, ChatRoomRequestDto requestDto) {
//        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
//        return entity.update(requestDto);
//    }

    //TODO: 채팅방 삭제 -> 이 로직은 전체 유저에게서 채팅방 삭제
//    @Override
//    public void delete(final Long id) {
//        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
//        this.chatRoomRepository.delete(entity);
//    }
}