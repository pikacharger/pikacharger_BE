package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
//import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;

import jakarta.transaction.Transactional;

public interface ChatRoomService {
    @Transactional
    ChatRoomResponseDto findByRoomId(Long id);

    //채팅방 생성
    @Transactional
    Long save(ChatRoomRequestDto requestDto);

    //TODO: 채팅방 삭제
//    void delete(Long id);

    //TODO: 마지막 메시지 조회
//    @Transactional
//    ChatLog getLastChatLog(Long chatRoomId);
}