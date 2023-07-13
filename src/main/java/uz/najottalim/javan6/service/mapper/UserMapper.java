package uz.najottalim.javan6.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.dto.userdto.UserDto;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.entity.User;
import uz.najottalim.javan6.repository.ProfileRepository;
import uz.najottalim.javan6.service.JWTGeneratorService;


@Service
@RequiredArgsConstructor
public class UserMapper {
    private final ProfileRepository profileRepository;
    private final JWTGeneratorService jwtGeneratorService;
    public UserDto toDto(User user){
        Profile profile = profileRepository.findByUserUsername(user.getUsername())
                .orElseThrow(() -> new NoResourceFoundException("No user found"));
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                profile.getBio(),
                profile.getImagePath(),
                jwtGeneratorService.jwtGenerate()
        );
    }
    public User toEntity(UserDto profileDto){
        return new User(
                profileDto.getId(),
                profileDto.getEmail(),
                profileDto.getUsername(),
                profileDto.getPassword()
        );
    }
}
