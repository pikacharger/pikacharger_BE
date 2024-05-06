package elice04_pikacharger.pikacharger.domain.image.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;

public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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
