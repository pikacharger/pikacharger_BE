package elice04_pikacharger.pikacharger.domain.user.entity;


import elice04_pikacharger.pikacharger.domain.common.BaseEntity;
import elice04_pikacharger.pikacharger.domain.user.dto.payload.UserEditPayload;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "USERS")
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column
    private String address;

    @Column
    private String phoneNumber;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserRole> roles = new ArrayList<>();

    @Column
    private String role;

    @Column(nullable = false, length = 50)
    private String chargerType;

    @Column
    private String profileImage;

    private String resignReason;
    private Boolean resign;

    public void addRole(UserRole role){
        List<UserRole> list = new ArrayList<>();
        list.add(role);
        this.roles = list;
        this.role = role.getRole().getName();
    }

    public void editNickname(UserEditPayload payload){
        this.nickname = payload.getNickname();
    }

    public void updatePassword(String password){
        this.password = password;
    }


//    @OneToMany(mappedBy = "user")
//    private List<Favorite> favorites = new ArrayList<>();
}
