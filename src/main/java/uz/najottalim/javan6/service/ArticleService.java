package uz.najottalim.javan6.service;

import org.springframework.http.ResponseEntity;
import uz.najottalim.javan6.dto.articledto.ArticleResponse;
import uz.najottalim.javan6.dto.articledto.ArticlesDto;
import uz.najottalim.javan6.dto.commentdto.CommentResponse;
import uz.najottalim.javan6.dto.commentdto.CommentsDto;

import java.util.Optional;

public interface ArticleService {
    ResponseEntity<ArticlesDto> getArticles(Integer limit, Integer offset, Optional<String> author,Optional<String> favorited,Optional<String> tag);

    ResponseEntity<ArticleResponse> getArticleBySlug(String slug);

    ResponseEntity<CommentsDto> getArticleComments(String slug);

    ResponseEntity<ArticlesDto> getArticlesByToken(Integer limit, Integer offset);

    ResponseEntity<ArticleResponse> addArticle(ArticleResponse articleResponse);

    ResponseEntity<ArticleResponse> likeArticle(String slug);

    ResponseEntity<CommentResponse> addComment(String slug, CommentResponse commentResponse);

    void deleteComment(String slug, Long id);

    ResponseEntity<ArticleResponse> deleteLike(String slug);

    void deleteArticle(String slug);

    ResponseEntity<ArticleResponse> updateArticle(String slug, ArticleResponse articleResponse);
}
