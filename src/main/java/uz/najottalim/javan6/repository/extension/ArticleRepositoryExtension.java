package uz.najottalim.javan6.repository.extension;

import uz.najottalim.javan6.entity.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepositoryExtension {
    List<Article> getArticlesPageable(Integer limit, Integer offset, Optional<String> author, Optional<String> favorited, Optional<String> tag);
    void likeArticle(Long articleId,Long userId);
    boolean isCurrentUserLiked(Long articleId,Long userId);

    List<Article> getArticlesByFollower(Long id,Integer limit, Integer offset);

    void deleteLike(Long userId, Long articleId);
}
