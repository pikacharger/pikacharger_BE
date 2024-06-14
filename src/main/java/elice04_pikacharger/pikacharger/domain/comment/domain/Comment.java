package elice04_pikacharger.pikacharger.domain.comment.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import elice04_pikacharger.pikacharger.domain.common.global.BaseEntity;
import elice04_pikacharger.pikacharger.domain.review.domain.Review;
import elice04_pikacharger.pikacharger.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ColumnDefault("FALSE")
    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    @JsonBackReference
    private Review review;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public void updateUser(User user){
        this.user = user;
    }

    public void updateReview(Review review){
        this.review = review;
    }

    public void updateParent(Comment parent) {
        this.parent = parent;
    }

    public void changeIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }

    public void updateContent(String content){
        this.content = content;
    }

    // 생성자 없애도 q 클래스에서 protected 에러 발생 안하는지 확인 필요.
    @Builder
    public Comment(String content, User user, Review review, Comment parent) {
        this.content = content;
        this.user = user;
        this.review = review;
        this.parent = parent;
        this.isDeleted = false;
    }
}
