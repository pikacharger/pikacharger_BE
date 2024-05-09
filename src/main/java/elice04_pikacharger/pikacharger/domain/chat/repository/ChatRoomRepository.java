package elice04_pikacharger.pikacharger.domain.chat.repository;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;
import elice04_pikacharger.pikacharger.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByIdAndUserId(Long chatRoomId, Long userId);
    List<ChatRoom> findBysenderAndReceiver(User sender, User receiver);
}