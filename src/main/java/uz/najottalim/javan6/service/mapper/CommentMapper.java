package uz.najottalim.javan6.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.dto.commentdto.CommentDto;
import uz.najottalim.javan6.entity.Article;
import uz.najottalim.javan6.entity.Comment;
import uz.najottalim.javan6.repository.ProfileRepository;

@Service
@RequiredArgsConstructor
public class CommentMapper {
    private final ArticleMapper articleMapper;
    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    public CommentDto toDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                profileMapper.toDto(comment.getProfile()),
                articleMapper.toDto(comment.getArticle()),
                comment.getBody(),
                comment.getCreateAt(),
                comment.getUpdateAt()
        );
    }
    public Comment toEntity(CommentDto commentDto){
        return new Comment(
                commentDto.getId(),
                null,
                null,
                commentDto.getBody(),
                commentDto.getCreateAt(),
                commentDto.getUpdateAt()
        );
    }
}
