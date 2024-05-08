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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatLogServiceImpl implements ChatLogService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatLogRepository chatLogRepository;

    // 전체 메시지 조회
    @Override
    public List<ChatLogResponseDto> getAllChatLog(Long chatRoomId) {
        ChatRoom chatRoomEntity = this.chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        List<ChatLog> chatLogs = chatLogRepository.findAllByChatRoom(chatRoomEntity);
        List<ChatLogResponseDto> chatLogResponseList = new ArrayList<>();

        for (ChatLog chatLog : chatLogs) {
            ChatLogResponseDto chatLogResponseDto = ChatLogResponseDto.builder()
                    .id(chatLog.getId())
                    .chatRoom(chatRoomEntity)
                    .sender(chatLog.getSender())
                    .messageContents(chatLog.getMessageContents())
                    .createDate(chatLog.getCreateDate())
                    .build();
            chatLogResponseList.add(chatLogResponseDto);
        }
        return chatLogResponseList;
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
//    public void deleteChatLog(final Long chatLogId) {
//        ChatLog chatLogEntity = this.chatLogRepository.findById(chatLogId).orElseThrow(
//                () -> new IllegalArgumentException("해당 메시지가 존재하지 않습니다."));
//        this.chatLogRepository.delete(chatLogEntity);
//    }
}