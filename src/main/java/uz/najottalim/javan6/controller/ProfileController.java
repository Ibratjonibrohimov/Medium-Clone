package uz.najottalim.javan6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.javan6.dto.profiledto.ProfileResponse;
import uz.najottalim.javan6.service.ProfileService;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getProfileByUsername(@PathVariable String username){
       return profileService.getProfileByUsername(username);
    }
    @PostMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> addFollower(@PathVariable String username){
        return profileService.addFollower(username);
    }

    @DeleteMapping("/{username}/follow")
    public ResponseEntity<ProfileResponse> deleteFollower(@PathVariable String username){
        return profileService.deleteFollower(username);
    }
}
