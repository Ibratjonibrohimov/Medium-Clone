package uz.najottalim.javan6.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.dto.articledto.ArticleDto;
import uz.najottalim.javan6.entity.Article;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.entity.Tag;
import uz.najottalim.javan6.repository.ArticleRepository;
import uz.najottalim.javan6.repository.ProfileRepository;
import uz.najottalim.javan6.repository.TagRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleMapper {
    private final ProfileMapper profileMapper;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ProfileRepository profileRepository;

    public ArticleDto toDto(Article article){
        ArticleDto articleDto = new ArticleDto(
                article.getId(),
                article.getTitle().replace(" ", "-") + "-" + article.getId(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                article.getTags().stream().map(Tag::getName).collect(Collectors.toList()),
                article.getCreateAt(),
                article.getUpdateAt(),
                false,
                articleRepository.getLikesCount(article.getId()),
                profileMapper.toDto(article.getProfile()),
                null
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        profileRepository.findByUserEmail(authentication.getName())
                        .ifPresent(value -> {
                            articleDto.setFavorite(articleRepository.isCurrentUserLiked(article.getId(),value.getId()));
                        });
        return articleDto;
    }

    public Article toEntity(ArticleDto articleDto , Profile profile){
        List<Tag> tags = new ArrayList<>();

        articleDto.getTags()
                .forEach(tag->{
                    if(tagRepository.findByName(tag).isEmpty()){
                        Tag save = tagRepository.save(new Tag(null, tag));
                        tags.add(save);
                    }
                    else {
                        tags.add(tagRepository.findByName(tag).get());
                    }
                });

        return new Article(
                articleDto.getId(),
                articleDto.getTitle(),
                articleDto.getDescription(),
                articleDto.getBody(),
                tags,
                articleDto.getCreatedAt(),
                articleDto.getUpdatedAt(),
                null,
                profile
        );
    }
}
