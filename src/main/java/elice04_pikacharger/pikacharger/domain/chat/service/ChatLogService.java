package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatLogService {

    // 전체 메시지 조회
    List<ChatLogResponseDto> getAllChatLog(Long chatRoomId, Long userId);

    // 메시지 저장
    @Transactional
    Long save(Long userId, Long chatRoomId, ChatLogRequestDto requestDto);

//    @Transactional
//    void deleteChatLog(Long chatMessageId);
}