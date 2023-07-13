package uz.najottalim.javan6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.dto.articledto.ArticleDto;
import uz.najottalim.javan6.dto.articledto.ArticleResponse;
import uz.najottalim.javan6.dto.articledto.ArticlesDto;
import uz.najottalim.javan6.dto.commentdto.CommentResponse;
import uz.najottalim.javan6.dto.commentdto.CommentsDto;
import uz.najottalim.javan6.entity.Article;
import uz.najottalim.javan6.entity.Comment;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.entity.User;
import uz.najottalim.javan6.repository.ArticleRepository;
import uz.najottalim.javan6.repository.CommentRepository;
import uz.najottalim.javan6.repository.ProfileRepository;
import uz.najottalim.javan6.repository.UserRepository;
import uz.najottalim.javan6.service.ArticleService;
import uz.najottalim.javan6.service.mapper.ArticleMapper;
import uz.najottalim.javan6.service.mapper.CommentMapper;
import uz.najottalim.javan6.service.mapper.ProfileMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    @Override
    public ResponseEntity<ArticlesDto> getArticles(Integer limit, Integer offset, Optional<String> author, Optional<String> favorited, Optional<String> tag) {

        return ResponseEntity.ok(
                new ArticlesDto(
                        articleRepository.getArticlesPageable(limit,offset,author,favorited,tag)
                                .stream().map(articleMapper::toDto).collect(Collectors.toList())
                )
        );
    }

    @Override
    public ResponseEntity<ArticleResponse> getArticleBySlug(String slug) {
        String[] split = slug.split("-");
        Long id = Long.parseLong(split[split.length-1]);
        return ResponseEntity.ok(new ArticleResponse(
         articleMapper.toDto(
                 articleRepository.findById(id).orElseThrow(()->new NoResourceFoundException("Article not found"))
         )
        ));
    }

    @Override
    public ResponseEntity<CommentsDto> getArticleComments(String slug) {
        String[] split = slug.split("-");
        Long id = Long.parseLong(split[split.length-1]);
        List<Comment> comments = commentRepository.findAllByProfileId(id);
        return ResponseEntity.ok(new CommentsDto(
                comments.stream().map(commentMapper::toDto).collect(Collectors.toList())
        ));
    }

    @Override
    public ResponseEntity<ArticlesDto> getArticlesByToken(Integer limit, Integer offset) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Profile user = profileRepository.findByUserEmail(authentication.getName())
                .orElseThrow(() -> new NoResourceFoundException(" user not found"));
        List<Article> articlesByFollower = articleRepository.getArticlesByFollower(user.getId(), limit, offset);
        return ResponseEntity.ok(new ArticlesDto(articlesByFollower
                .stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList()))
        );
    }

    @Override
    public ResponseEntity<ArticleResponse> addArticle(ArticleResponse articleResponse) {

        LocalDateTime now = LocalDateTime.now();
        articleResponse.getArticle().setCreatedAt(now);
        articleResponse.getArticle().setUpdatedAt(now);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        Profile profile = profileRepository.findByUserEmail(name)
                .orElseThrow(() -> new NoResourceFoundException("profile not found"));

        Article save = articleRepository.save(articleMapper.toEntity(articleResponse.getArticle(), profile));
        return ResponseEntity.ok(new ArticleResponse(articleMapper.toDto(save)));
    }

    @Override
    public ResponseEntity<ArticleResponse> likeArticle(String slug) {
        Long id = Long.parseLong(slug.substring(slug.lastIndexOf("-")+1));
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoResourceFoundException(" article not found"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));

        if(!articleRepository.isCurrentUserLiked(id, user.getId())){ articleRepository.likeArticle(id,user.getId());}

        ArticleDto articleDto = articleMapper.toDto(article);
        articleDto.setFavorite(articleRepository.isCurrentUserLiked(id, user.getId()));
        articleDto.setFavoritedBy(
                profileRepository.findAllByLikedArticle(id).stream()
                        .map(profileMapper::toDto)
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(new ArticleResponse(articleDto));
    }

    @Override
    public ResponseEntity<CommentResponse> addComment(String slug, CommentResponse commentResponse) {
        Long id = Long.parseLong(slug.substring(slug.lastIndexOf("-")+1));
        Comment comment = commentMapper.toEntity(commentResponse.getComment());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Profile profile = profileRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoResourceFoundException("article not found"));

        comment.setArticle(article);
        comment.setProfile(profile);

        LocalDateTime now = LocalDateTime.now();

        comment.setCreateAt(now);
        comment.setUpdateAt(now);

        Comment save = commentRepository.save(comment);
        return ResponseEntity.ok(new CommentResponse(commentMapper.toDto(comment)));
    }

    @Override
    public void deleteComment(String slug, Long id) {
        Long articleId = Long.parseLong(slug.substring(slug.lastIndexOf("-")+1));
        Comment comment = commentRepository.findByIdAndArticleId(id, articleId)
                .orElseThrow(() -> new NoResourceFoundException(" comment not found"));

        commentRepository.delete(comment);
    }

    @Override
    public ResponseEntity<ArticleResponse> deleteLike(String slug) {
        Long articleId = Long.parseLong(slug.substring(slug.lastIndexOf("-")+1));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Profile user = profileRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));

        articleRepository.deleteLike(user.getId(),articleId);
        return null;
    }

    @Override
    public void deleteArticle(String slug) {
        Long id = Long.parseLong(slug.substring(slug.lastIndexOf("-")+1));

        articleRepository.findById(id)
                .orElseThrow(()-> new NoResourceFoundException(" article not found"));

        articleRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<ArticleResponse> updateArticle(String slug, ArticleResponse articleResponse) {

        Long id = Long.parseLong(slug.substring(slug.lastIndexOf("-")+1));
        articleRepository.findById(id)
                .orElseThrow(()-> new NoResourceFoundException(" article not found"));
        articleResponse.getArticle().setId(id);
        Profile profile = profileRepository.findByUserUsername(articleResponse.getArticle().getProfileDto().getUsername())
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        Article save = articleRepository.save(articleMapper.toEntity(articleResponse.getArticle(), profile));

        return ResponseEntity.ok(new ArticleResponse(articleMapper.toDto(save)));
    }
}
