package elice04_pikacharger.pikacharger.domain.chat.repository;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByRoomId(Long id);

    List<ChatRoom> findAllByUserId(Long id);

    List<ChatRoom> findAllChatRoom(User sender, User receiver);
}