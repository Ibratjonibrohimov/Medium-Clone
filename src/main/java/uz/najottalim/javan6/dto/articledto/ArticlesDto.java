package uz.najottalim.javan6.dto.articledto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.najottalim.javan6.dto.articledto.ArticleDto;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesDto {
    private List<ArticleDto> articles;
}
