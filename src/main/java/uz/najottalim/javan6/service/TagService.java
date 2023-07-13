package uz.najottalim.javan6.service;

import org.springframework.http.ResponseEntity;
import uz.najottalim.javan6.dto.tagdto.TagNameListDto;

public interface TagService {
    ResponseEntity<TagNameListDto> getPopularTags();
}
