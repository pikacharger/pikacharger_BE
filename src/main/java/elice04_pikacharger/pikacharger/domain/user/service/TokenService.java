package elice04_pikacharger.pikacharger.domain.user.service;

import com.nimbusds.oauth2.sdk.token.RefreshToken;
import elice04_pikacharger.pikacharger.domain.user.entity.RefreshTokens;
import elice04_pikacharger.pikacharger.domain.user.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    @Transactional
    public String deleteByUserId(Long userId) {
        try{
            tokenRepository.deleteByUserId(userId);
        } catch (Exception e) {
            return "there is no RefreshToken";
        }

        return "success";
    }

    @Transactional
    public String save(RefreshTokens refreshToken) {
        tokenRepository.save(refreshToken);

        return "token saved";
    }

    public String validate(String refreshToken) {
        String token = "";

        if(refreshToken != null && refreshToken.startsWith("Bearer ")) {
            token = refreshToken.substring(7);
        }

        RefreshTokens findToken = tokenRepository.findByRefreshToken(token).orElseThrow();

        return findToken.getEmail();
    }
}
