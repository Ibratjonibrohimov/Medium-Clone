package uz.najottalim.javan6.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.najottalim.javan6.entity.Article;
import uz.najottalim.javan6.repository.extension.ArticleRepositoryExtension;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long>, ArticleRepositoryExtension {
    @Query(
        value = "select count(*) from likes where article_id = ?1",nativeQuery = true
    )
    Long getLikesCount(Long article_id);
    List<Article> findAllByProfile_UserEmail(String email);


}
