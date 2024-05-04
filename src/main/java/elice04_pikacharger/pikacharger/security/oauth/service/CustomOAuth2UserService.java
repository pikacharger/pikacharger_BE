package elice04_pikacharger.pikacharger.security.oauth.service;

import elice04_pikacharger.pikacharger.domain.user.entity.ProviderType;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.domain.user.repository.UserRepository;
import elice04_pikacharger.pikacharger.security.oauth.CustomOAuth2User;
import elice04_pikacharger.pikacharger.security.oauth.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        ProviderType providerType = getProviderType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(providerType, userNameAttributeName,attributes);

        User createdUser = getUser(extractAttributes, providerType);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(createdUser.getRoleKey())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getUsername(),
                createdUser.getRole()
        );
    }

    private ProviderType getProviderType(String registrationId) {
        if(KAKAO.equals(registrationId)) {
            return ProviderType.KAKAO;
        }
        return ProviderType.GOOGLE;
    }

    private User getUser(OAuthAttributes extractAttributes, ProviderType providerType) {
        User findUser = userRepository.findByProviderTypeAndSocialId(providerType,
                extractAttributes.getOauth2UserInfo().getId()).orElse(null);

        if(findUser == null) {
            return saveUser(extractAttributes, providerType);
        }
        return findUser;
    }

    private User saveUser(OAuthAttributes extractAttributes, ProviderType providerType) {
        User createdUser = extractAttributes.toEntity(providerType, extractAttributes.getOauth2UserInfo());
        return userRepository.save(createdUser);
    }
}
