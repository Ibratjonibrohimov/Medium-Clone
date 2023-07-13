package uz.najottalim.javan6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.dto.tagdto.TagNameListDto;
import uz.najottalim.javan6.repository.TagRepository;
import uz.najottalim.javan6.service.TagService;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    @Override
    public ResponseEntity<TagNameListDto> getPopularTags() {
        return ResponseEntity.ok(new TagNameListDto(tagRepository.getPopularTags()));
    }
}
