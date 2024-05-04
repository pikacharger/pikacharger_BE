package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;

import org.springframework.transaction.annotation.Transactional;

public interface ChatLogService {
    @Transactional
    ChatLogResponseDto findChatLogById(Long chatLogId);

    @Transactional
    Long save(Long chatRoomId, ChatLogRequestDto requestDto);

//    @Transactional
//    void deleteChatLog(Long chatMessageId);
}
