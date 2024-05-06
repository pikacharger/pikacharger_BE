package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatLogService {

    // 전체 메시지 조회
    List<ChatLogResponseDto> getAllChatLog(Long chatRoomId);

    @Transactional
    Long save(Long chatRoomId, ChatLogRequestDto requestDto);

//    @Transactional
//    void deleteChatLog(Long chatMessageId);
}