package elice04_pikacharger.pikacharger.security;

import elice04_pikacharger.pikacharger.domain.user.entity.User;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.picture = user.getProfileImage();
    }
}
