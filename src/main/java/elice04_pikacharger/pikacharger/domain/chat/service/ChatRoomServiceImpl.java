package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.charger.entity.Charger;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.CreateChatRoomResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.SingleChatRoomResponseDto;
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

    // 단일 채팅방 조회
    @Override
    @Transactional
    public SingleChatRoomResponseDto findById(final Long id) {
        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채팅방입니다. id = " + id));
        return SingleChatRoomResponseDto.toEntity(entity);
    }

    // 전체 채팅방 조회
    @Override
    @Transactional
    public List<ChatRoomResponseDto> findAllChatRoom(Long userId) {
        List<ChatRoom> senderChatRooms = chatRoomRepository.findByUserId(userId);
        List<ChatRoom> receiverChatRooms = chatRoomRepository.findByReceiverIdId(userId);

        List<ChatRoomResponseDto> chatRoomResponseDto = new ArrayList<>();

        for (ChatRoom chatRoom : senderChatRooms) {
            chatRoomResponseDto.add(ChatRoomResponseDto.toEntity(chatRoom));
        }
        for (ChatRoom chatRoom : receiverChatRooms) {
            chatRoomResponseDto.add(ChatRoomResponseDto.toEntity(chatRoom));
        }
        return chatRoomResponseDto;
    }

    @Override
    @Transactional
    public CreateChatRoomResponseDto save(Long chargerId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("해당 유저가 존재하지 않습니다."));
        Charger charger = chargerRepository.findById(chargerId).orElseThrow(() -> new NoSuchElementException("해당하는 충전소가 존재하지 않습니다"));

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByChargerIdAndUserId(chargerId, userId);

        if (existingChatRoom.isPresent()) {
            return CreateChatRoomResponseDto.toDto(existingChatRoom.get()); // 이미 존재하는 채팅방 반환
        } else {
            ChatRoom chatRoom = ChatRoom.builder()
                    .charger(charger)
                    .user(user)
                    .receiverId(charger.getUser()) // 채팅방에 대상 사용자 추가
                    .build();

            chatRoomRepository.save(chatRoom); // 채팅방 저장

            return CreateChatRoomResponseDto.toDto(chatRoom);
        }
    }

    // TODO : 채팅방 삭제 -> 이 로직은 전체 유저에게서 채팅방 삭제하고 있다
    // TODO : 삭제를 요청한 유저의 채팅방에서만 삭제하기
//    @Override
//    public void delete(final Long id) {
//        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
//        this.chatRoomRepository.delete(entity);
//    }
}