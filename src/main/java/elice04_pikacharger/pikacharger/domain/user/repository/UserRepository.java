package elice04_pikacharger.pikacharger.domain.user.repository;

import elice04_pikacharger.pikacharger.domain.user.entity.ProviderType;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);
    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    Optional<User> findByProviderTypeAndSocialId(ProviderType providerType, String id);
}
