package elice04_pikacharger.pikacharger.domain.image.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "profile_image")
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user; //이름 관련 찾아보기

    @Column(name = "img_url")
    private String imageUrl;

    @Builder
    public ProfileImage(User user, String imageUrl){
        this.user = user;
        this.imageUrl = imageUrl;
    }
}
