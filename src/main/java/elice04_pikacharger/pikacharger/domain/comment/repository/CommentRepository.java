package elice04_pikacharger.pikacharger.domain.comment.repository;

import elice04_pikacharger.pikacharger.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> , CommentCustomRepository{
}
