package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
//import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;

import jakarta.transaction.Transactional;

import java.util.List;

public interface ChatRoomService {

    //채팅방 생성
    @Transactional
    ChatRoomRequestDto save(Long chaegerId, Long senderId);

    // 전체 채팅방 조회
    @Transactional
    List<ChatRoomResponseDto> findAllChatRoom(Long userId);

    //TODO: 채팅방 삭제
//    void delete(Long id);

    //TODO: 마지막 메시지 조회
//    @Transactional
//    ChatLog getLastChatLog(Long chatRoomId);
}