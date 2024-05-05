package elice04_pikacharger.pikacharger.domain.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RefreshTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column(name = "user_email")
    private String email;
    @Column
    private String refreshToken;

    @Builder
    public RefreshTokens(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }
}
