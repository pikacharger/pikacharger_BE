//package elice04_pikacharger.pikacharger.domain.chat.service;
//
//import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomRequestDto;
//import elice04_pikacharger.pikacharger.domain.chat.dto.ChatRoomResponseDto;
//import elice04_pikacharger.pikacharger.domain.chat.repository.ChatRoomRepository;
//import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
//
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomServiceImpl implements ChatRoomService {
//    private final ChatRoomRepository chatRoomRepository;
//
//    // 채팅방 조회
//    @Override
//    @Transactional
//    public ChatRoomResponseDto findByRoomId(final Long id) {
//        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
//        return new ChatRoomResponseDto(entity);
//    }
//
//    // 채팅방 생성
//    @Override
//    @Transactional
//    public Long save(final ChatRoomRequestDto requestDto) {
//        return this.chatRoomRepository.save(requestDto.toEntity()).getId();
//    }
//
//    //TODO: 마지막 메시지 조회
////    @Override
////    @Transactional
////    public ChatLog getLastChatLog(Long id) {
////        ChatRoom chatRoom = chatRoomRepository.findByRoomId(id);
////        if (chatRoom == null) {
////            throw new IllegalArgumentException("채팅방이 존재하지 않습니다.");
////        }
////        return chatRoom.getLastChatLog();
////    }
//
//    // 마지막 메시지를 채팅방에서 업데이트
////    @Override
////    @Transactional
////    public Long update(final Long id, ChatRoomRequestDto requestDto) {
////        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
////        return entity.update(requestDto);
////    }
//
//    //TODO: 채팅방 삭제 -> 전체 유저에게서 채팅방 삭제
////    @Override
////    public void delete(final Long id) {
////        ChatRoom entity = this.chatRoomRepository.findById(id).orElseThrow(
////                () -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
////        this.chatRoomRepository.delete(entity);
////    }
//}