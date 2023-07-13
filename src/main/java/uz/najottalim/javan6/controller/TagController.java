package uz.najottalim.javan6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.najottalim.javan6.dto.tagdto.TagNameListDto;
import uz.najottalim.javan6.service.TagService;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping()
    public ResponseEntity<TagNameListDto> getPopularTags(){
        return tagService.getPopularTags();
    }
}
