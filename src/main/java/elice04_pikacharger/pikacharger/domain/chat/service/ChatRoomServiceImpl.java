package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.repository.ChatRoomRepository;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import elice04_pikacharger.pikacharger.domain.charger.repository.ChargerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChargerRepository chargerRepository;

    // 전체 채팅방 조회
    @Override
    @Transactional
    public List<ChatRoomResponseDto> findAllChatRoom(Long userId) {
        List<ChatRoom> senderChatRooms = chatRoomRepository.findByUserId(userId);
        List<ChatRoom> receiverChatRooms = chatRoomRepository.findByReceiverIdId(userId);

        List<ChatRoomResponseDto> chatRoomResponseDto = new ArrayList<>();
        for (ChatRoom chatRoom : senderChatRooms) {
            chatRoomResponseDto.add(ChatRoomResponseDto.toEntity(chatRoom.getId(), chatRoom.getReceiverId(), chatRoom, chatRoom.getLastChatLog()));
        }
        for (ChatRoom chatRoom : receiverChatRooms) {
            chatRoomResponseDto.add(ChatRoomResponseDto.toEntity(chatRoom.getId(), chatRoom.getUser(), chatRoom, chatRoom.getLastChatLog()));
        }
        return chatRoomResponseDto;
    }

    @Override
    @Transactional
    public ChatRoomRequestDto save(Long chargerId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
        Charger charger = chargerRepository.findById(chargerId).orElseThrow(() -> new NoSuchElementException("해당하는 충전소가 존재하지 않습니다"));

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByChargerIdAndUserId(chargerId, userId);

        if (existingChatRoom.isPresent()) {
            return ChatRoomRequestDto.toDto(existingChatRoom.get()); // 이미 존재하는 채팅방 반환
        } else {
            ChatRoom chatRoom = ChatRoom.builder()
                    .charger(charger)
                    .user(user)
                    .receiverId(charger.getUser()) // 채팅방에 대상 사용자 추가
                    .build();

            chatRoomRepository.save(chatRoom); // 채팅방 저장

            return ChatRoomRequestDto.toDto(chatRoom);
        }
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