package elice04_pikacharger.pikacharger.domain.chat.dto;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatLog;
import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private Long userId;
    private String userProfileImg;
    private String userNickname;
    private Long receiverId;
    private String receiverProfileImg;
    private String receiverNickname;
    private LocalDateTime createDate;
    private String lastMessage;

    public static ChatRoomResponseDto toEntity(ChatRoom chatRoom) {
        ChatLog lastChatLog = chatRoom.getLastChatLog();
        User user = chatRoom.getUser();
        User receiver = chatRoom.getReceiverId();

        if (user == null || receiver == null) {
            throw new IllegalArgumentException("User and Receiver cannot be null");
        }

        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                .userId(user.getId())
                .userProfileImg(user.getProfileImage())
                .userNickname(user.getNickName())
                .receiverId(receiver.getId())
                .receiverProfileImg(receiver.getProfileImage())
                .receiverNickname(receiver.getNickName())
                .createDate(chatRoom.getCreateDate())
                .lastMessage(lastChatLog != null ? lastChatLog.getMessageContents() : "대화를 기다리고 있어요. 무엇이든 물어보세요!")
                .build();
    }

    @Builder
    public ChatRoomResponseDto(Long chatRoomId, Long userId, String userProfileImg, String userNickname, LocalDateTime createDate, String lastMessage, Long receiverId, String receiverProfileImg, String receiverNickname) {
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.userProfileImg = userProfileImg;
        this.userNickname = userNickname;
        this.receiverId = receiverId;
        this.receiverProfileImg = receiverProfileImg;
        this.receiverNickname = receiverNickname;
        this.createDate = createDate;
        this.lastMessage = lastMessage;
    }
}