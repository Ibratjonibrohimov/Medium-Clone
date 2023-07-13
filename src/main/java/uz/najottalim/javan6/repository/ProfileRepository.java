package uz.najottalim.javan6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.repository.extension.ProfileRepositoryExtension;

import java.util.*;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long>, ProfileRepositoryExtension {
    Optional<Profile> findByUserUsername(String username);
    Optional<Profile> findByUserEmail(String email);
    @Query(
            value = "select profile.* from profile join likes l on profile.id = l.user_id where l.article_id = ?1",
            nativeQuery = true
    )
    List<Profile> findAllByLikedArticle(Long articleId);
}
