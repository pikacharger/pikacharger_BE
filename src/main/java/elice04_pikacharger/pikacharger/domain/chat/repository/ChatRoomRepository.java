package elice04_pikacharger.pikacharger.domain.chat.repository;

import elice04_pikacharger.pikacharger.domain.chat.entity.ChatRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByIdAndUserId(Long chatRoomId, Long userId);

    List<ChatRoom> findByUserId(Long userId);

    List<ChatRoom> findByReceiverIdId(Long receiverId);

    Optional<ChatRoom> findByChargerIdAndUserId(Long chargerId, Long userId);
}