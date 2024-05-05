package elice04_pikacharger.pikacharger.domain.user.repository;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import elice04_pikacharger.pikacharger.domain.user.entity.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshTokens, Long> {
    void deleteByUserId(Long id);

    Optional<RefreshTokens> findByRefreshToken(String refreshToken);

    void deleteByEmail(String userEmail);
}
