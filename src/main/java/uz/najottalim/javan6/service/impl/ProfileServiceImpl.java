package uz.najottalim.javan6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.dto.profiledto.ProfileDto;
import uz.najottalim.javan6.dto.profiledto.ProfileResponse;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.repository.ProfileRepository;
import uz.najottalim.javan6.service.ProfileService;
import uz.najottalim.javan6.service.mapper.ProfileMapper;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    @Override
    public ResponseEntity<ProfileResponse> getProfileByUsername(String username) {
        Profile profile = profileRepository.findByUserUsername(username)
                .orElseThrow(()->new NoResourceFoundException("No profile found"));
        return ResponseEntity.ok(new ProfileResponse(profileMapper.toDto(profile)));
    }

    @Override
    public ResponseEntity<ProfileResponse> addFollower(String username) {
        Profile user = profileRepository.findByUserUsername(username)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile follower = profileRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        profileRepository.addFollower(user.getId(),follower.getId());
        ProfileDto profileDto = profileMapper.toDto(user);
        boolean currentUserFollow = profileRepository.isCurrentUserFollow(user.getId(), follower.getId());
        profileDto.setFollowing(currentUserFollow);
        return ResponseEntity.ok(new ProfileResponse(profileDto));
    }

    @Override
    public ResponseEntity<ProfileResponse> deleteFollower(String username) {
        Profile user = profileRepository.findByUserUsername(username)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Profile follower = profileRepository.findByUserEmail(email)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        profileRepository.deleteFollower(user.getId(),follower.getId());
        return ResponseEntity.ok(new ProfileResponse(profileMapper.toDto(user)));
    }
}
