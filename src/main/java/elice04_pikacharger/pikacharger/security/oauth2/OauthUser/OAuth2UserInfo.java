package elice04_pikacharger.pikacharger.security.oauth2.OauthUser;

import java.util.Map;

public interface OAuth2UserInfo {
    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

    String getProfileImageUrl();
}
