package elice04_pikacharger.pikacharger.domain.user.entity;


import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.UserEditPayload;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.Profile;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @Column(length = 50)
    private String chargerType;

    @Column
    private String profileImage;
    private String resignReason;
    private Boolean resign;
    private String refreshToken;
    private ProviderType providerType;
    private String socialId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    public User(String username, String email, String nickname, ProviderType providerType){
        this.username = username;
        this.email = email != null ? email : "NO_EMAIL";
        this.nickname = nickname;
        this.password = "NO_PASS";
        this.profileImage = profileImage != null ? profileImage : "NO_IMAGE";
        this.roles = new HashSet<>();
        this.providerType = providerType;

    }

    public void editNickname(UserEditPayload payload){
        this.nickname = payload.getNickname();
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public User updateImage(String profileImage){
        this.profileImage = profileImage;
        return this;
    }

    public void updateUser(UserEditPayload userEditPayload){
        this.nickname = userEditPayload.getNickname();
        this.profileImage = userEditPayload.getProfileImage();
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public String getNickName() {
        return nickname;
    }


//    @OneToMany(mappedBy = "user")
//    private List<Favorite> favorites = new ArrayList<>();
}
