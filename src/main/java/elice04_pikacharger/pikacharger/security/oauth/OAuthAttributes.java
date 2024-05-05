package elice04_pikacharger.pikacharger.security.oauth;

import elice04_pikacharger.pikacharger.domain.user.entity.ProviderType;
import elice04_pikacharger.pikacharger.domain.user.entity.Role;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import elice04_pikacharger.pikacharger.security.oauth.info.GoogleOAuth2UserInfo;
import elice04_pikacharger.pikacharger.security.oauth.info.KakaoOAuth2UserInfo;
import elice04_pikacharger.pikacharger.security.oauth.info.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
public class OAuthAttributes {

    private String nameAttributeKey;
    private OAuth2UserInfo oauth2UserInfo;

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(ProviderType providerType, String userNameAttributeName, Map<String, Object> attributes){

        if(providerType == ProviderType.KAKAO){
            return ofKakao(userNameAttributeName, attributes);
        }
        return ofGoogle(userNameAttributeName, attributes);

    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public User toEntity(ProviderType providerType, OAuth2UserInfo userInfo){
        Set<Role> initialRoles = new HashSet<>();
        initialRoles.add(Role.GUEST);
        return User.builder()
                .providerType(providerType)
                .socialId(userInfo.getId())
                .email(UUID.randomUUID() + "@socalUser.com")
                .nickname(oauth2UserInfo.getNickname())
                .profileImage(oauth2UserInfo.getImageUrl())
                .roles(initialRoles)
                .build();
    }

}
