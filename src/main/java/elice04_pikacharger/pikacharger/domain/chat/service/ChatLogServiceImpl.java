package elice04_pikacharger.pikacharger.domain.chat.service;

import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogRequestDto;
import elice04_pikacharger.pikacharger.domain.chat.dto.ChatLogResponseDto;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.chat.repository.ChatLogRepository;
import elice04_pikacharger.pikacharger.domain.chat.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatLogServiceImpl implements ChatLogService {
    private ChatRoomRepository chatRoomRepository;
    private ChatLogRepository chatLogRepository;

    //메시지 조회
    @Override
    @Transactional
    public ChatLogResponseDto findChatLogById(final Long chatLogId) {
        ChatLog chatLogEntity = this.chatLogRepository.findById(chatLogId).orElseThrow(
                () -> new IllegalArgumentException("메시지가 존재하지 않습니다."));
        return new ChatLogResponseDto(chatLogEntity);
    }

    //메시지 생성
    @Override
    @Transactional
    public Long save(final Long chatRoomId, final ChatLogRequestDto requestDto) {
        ChatRoom chatRoomEntity = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
        requestDto.setChatRoom(chatRoomEntity);
        return this.chatLogRepository.save(requestDto.toEntity()).getId();
    }

    //TODO: 메시지 삭제
//    @Override
//    @Transactional
//    public void deleteChatLog(final Long chatMessageId) {
//        ChatLog chatMessageEntity = this.chatLogRepository.findById(chatMessageId).orElseThrow(
//                () -> new IllegalArgumentException("해당 메시지가 존재하지 않습니다."));
//        this.chatLogRepository.delete(chatMessageEntity);
//    }
}
