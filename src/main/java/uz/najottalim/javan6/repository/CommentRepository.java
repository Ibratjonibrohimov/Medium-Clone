package uz.najottalim.javan6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.najottalim.javan6.entity.Comment;

import java.util.*;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByProfileId(Long id);
    Optional<Comment> findByIdAndArticleId(Long id, Long articleId);
}
