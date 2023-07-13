package uz.najottalim.javan6.dto.commentdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.najottalim.javan6.dto.profiledto.ProfileDto;
import uz.najottalim.javan6.dto.articledto.ArticleDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("comment")
public class CommentDto {
    private Long id;
    @JsonProperty(value = "author")
    private ProfileDto profileDto;
    @JsonProperty(value = "article")
    private ArticleDto articleDto;

    private String body;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
