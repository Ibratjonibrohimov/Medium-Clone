package uz.najottalim.javan6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.javan6.dto.articledto.ArticleResponse;
import uz.najottalim.javan6.dto.articledto.ArticlesDto;
import uz.najottalim.javan6.dto.commentdto.CommentResponse;
import uz.najottalim.javan6.dto.commentdto.CommentsDto;
import uz.najottalim.javan6.service.ArticleService;

import java.util.Optional;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    @GetMapping()
    public ResponseEntity<ArticlesDto> getArticles(@RequestParam Integer limit,
                                                   @RequestParam Integer offset,
                                                   @RequestParam Optional<String> author,
                                                   @RequestParam Optional<String> favorited,
                                                   @RequestParam Optional<String> tag){
        return articleService.getArticles(limit,offset,author,favorited,tag);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponse> getArticleBySlug(@PathVariable String slug){
        return articleService.getArticleBySlug(slug);
    }
    @GetMapping("/{slug}/comments")
    public ResponseEntity<CommentsDto> getArticleComments(@PathVariable String slug){
        return articleService.getArticleComments(slug);
    }

    @GetMapping("/feed")
    public ResponseEntity<ArticlesDto> getArticlesByToken(@RequestParam Integer limit,
                                                          @RequestParam Integer offset){
        return articleService.getArticlesByToken(limit,offset);
    };

    @PostMapping("/")
    public ResponseEntity<ArticleResponse> addArticle(@RequestBody ArticleResponse articleResponse){
        return articleService.addArticle(articleResponse);
    }

    @PostMapping("/{slug}/favorite")
    public ResponseEntity<ArticleResponse> likeArticle(@PathVariable String slug){
        return articleService.likeArticle(slug);
    }
    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<ArticleResponse> deleteLike(@PathVariable String slug){
        return articleService.deleteLike(slug);
    }
    @PostMapping("/{slug}/comments")
    public ResponseEntity<CommentResponse> addComment(@PathVariable String slug, @RequestBody CommentResponse commentResponse){
        return articleService.addComment(slug,commentResponse);
    }

    @DeleteMapping("/{slug}/comments/{id}")
    public void deleteComment(@PathVariable String slug, @PathVariable Long id){
        articleService.deleteComment(slug,id);
    }

    @DeleteMapping("/{slug}")
    public void deleteArticle(@PathVariable String slug){
        articleService.deleteArticle(slug);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable String slug, @RequestBody ArticleResponse articleResponse){
        return  articleService.updateArticle(slug,articleResponse);
    }

}
