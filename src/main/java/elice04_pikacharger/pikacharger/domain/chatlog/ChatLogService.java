package elice04_pikacharger.pikacharger.domain.chatlog;

import elice04_pikacharger.pikacharger.domain.chatroom.ChatRoom;
import elice04_pikacharger.pikacharger.domain.chatroom.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatLogService {
//    private final ChatLogRepository chatLogRepository;
//    private final UserRepository userRepository;
//
//    @Transactional
//    public ChatLogResponseDto postChatLog(Long chatRoomId, ChatLogRequestDto chatLogRequest) {
//        User user = userRepository.findOne(chatLogRequest.getUserId());
//        ChatRoom chatRoom = ChatRoomRepository.findOneChatRoom (chatRoomId);
//        ChatLog chatLog = ChatLog.builder()
//                .user(user)
//                .chatRoom(chatRoom)
//                .messageContents(chatLogRequest.getMessageContents())
//                .created(new Date())
//                .build();
//
//        Long chatLogId = chatLogRepository.createChatlog(chatLog);
//        return ChatLogResponseDto.builder()
//                .chatLogId(chatLogId)
//                .chatRoomId(chatRoom.getId())
//                .userId(user.getId())
//                .messageContents(chatLog.getMessage())
//                .created(chatLog.getCreated())
//                .build();
//    }
}
