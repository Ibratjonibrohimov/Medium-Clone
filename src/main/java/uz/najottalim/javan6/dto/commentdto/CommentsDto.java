package uz.najottalim.javan6.dto.commentdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private List<CommentDto> comments;

}
