package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private List<UserProfile> users;
    private LocalDateTime createDate;
    private String lastMessage;

    @Data
    @AllArgsConstructor
    public static class UserProfile {
        private Long userId;
        private String userProfileImg;
        private String userNickname;
    }

    public static ChatRoomResponseDto toEntity(ChatRoom chatRoom) {
        ChatLog lastChatLog = chatRoom.getLastChatLog();
        User user = chatRoom.getUser();
        User receiver = chatRoom.getReceiverId();

        if (user == null || receiver == null) {
            throw new IllegalArgumentException("User and Receiver cannot be null");
        }

        List<UserProfile> userProfiles = List.of(
                new UserProfile(user.getId(), user.getProfileImage(), user.getNickName()),
                new UserProfile(receiver.getId(), receiver.getProfileImage(), receiver.getNickName())
        );

        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                .users(userProfiles)
                .createDate(chatRoom.getCreateDate())
                .lastMessage(lastChatLog != null ? lastChatLog.getMessageContents() : "대화를 기다리고 있어요. 무엇이든 물어보세요!")
                .build();
    }

    public ChatRoomResponseDto(Long chatRoomId, List<UserProfile> users, LocalDateTime createDate, String lastMessage) {
        this.chatRoomId = chatRoomId;
        this.users = users;
        this.createDate = createDate;
        this.lastMessage = lastMessage;
    }
}