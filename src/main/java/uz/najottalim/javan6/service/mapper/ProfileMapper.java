package uz.najottalim.javan6.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.dto.profiledto.ProfileDto;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileMapper {
    private final UserRepository userRepository;
    public ProfileDto toDto(Profile profile){
        return new ProfileDto(
                profile.getId(),
                profile.getUser().getUsername(),
                profile.getBio(),
                profile.getImagePath(),
                profile.getFollowing()
        );
    }

    public Profile toEntity(ProfileDto profileDto){
        return new Profile(
                profileDto.getId(),
                profileDto.getBio(),
                profileDto.getImagePath(),
                profileDto.getFollowing(),
                userRepository.findByUsername(profileDto.getUsername())
                        .orElseThrow(()-> new NoResourceFoundException("user not found"))
        );
    }
}
