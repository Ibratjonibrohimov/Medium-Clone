package uz.najottalim.javan6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.najottalim.javan6.customexseptions.EmailAlreadyRegisteredException;
import uz.najottalim.javan6.customexseptions.NoResourceFoundException;
import uz.najottalim.javan6.customexseptions.UsernameAlreadyRegisteredException;
import uz.najottalim.javan6.dto.userdto.UserResponse;
import uz.najottalim.javan6.entity.Profile;
import uz.najottalim.javan6.entity.User;
import uz.najottalim.javan6.repository.ProfileRepository;
import uz.najottalim.javan6.repository.UserRepository;
import uz.najottalim.javan6.service.UserService;
import uz.najottalim.javan6.service.mapper.UserMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<UserResponse> loginUser(UserResponse userResponse) {
        User user = userRepository
                .findByEmail(userResponse.getUser().getEmail())
                        .orElseThrow(() -> new NoResourceFoundException("Not found"));
        if( ! passwordEncoder.matches(userResponse.getUser().getPassword(),user.getPassword())){
            throw  new NoResourceFoundException("not authenticated");
        };

        Authentication auth = new UsernamePasswordAuthenticationToken
                (user.getEmail(), null,List.of(new SimpleGrantedAuthority("user")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok(new UserResponse(userMapper.toDto(user)));
    }

    @Override
    public ResponseEntity<UserResponse> registerUser(UserResponse userResponse) {

        userRepository.findByUsername(userResponse.getUser().getUsername())
                .ifPresent(value -> {throw new UsernameAlreadyRegisteredException("has already been taken");
                });

        userRepository.findByEmail(userResponse.getUser().getEmail())
                        .ifPresent(value -> {throw new EmailAlreadyRegisteredException("has already been taken");
                        });

        userResponse.getUser().setPassword(passwordEncoder.encode(userResponse.getUser().getPassword()));

        User save = userRepository.save(userMapper.toEntity(userResponse.getUser()));

        Profile profile = new Profile(
                null,"", "https://st2.depositphotos.com/25790974/44392/v/600/depositphotos_443928634-stock-illustration-smile-happy-face-vector-design.jpg", false,save
        );
        profileRepository.save(profile);
        Authentication auth = new UsernamePasswordAuthenticationToken
                (save.getEmail(), null,List.of(new SimpleGrantedAuthority("user")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return ResponseEntity.ok( new UserResponse(userMapper.toDto(save)));
    }
    @Override
    public ResponseEntity<UserResponse> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        return ResponseEntity.ok(new UserResponse(userMapper.toDto(user)));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(UserResponse userResponse) {
        Profile profile = profileRepository.findByUserEmail(userResponse.getUser().getEmail())
                .orElseThrow(() -> new NoResourceFoundException("user not found"));

        userRepository.findByUsername(userResponse.getUser().getUsername())
                .ifPresent(value -> {throw new UsernameAlreadyRegisteredException("has already been taken");
                });

        userRepository.findByEmail(userResponse.getUser().getEmail())
                .ifPresent(value -> {throw new EmailAlreadyRegisteredException("has already been taken");
                });


        profile.setBio(userResponse.getUser().getBio());
        profile.setImagePath(userResponse.getUser().getImagePath());
        User user = profile.getUser();
        user.setUsername(userResponse.getUser().getUsername());
        user.setPassword(userResponse.getUser().getPassword());
        user.setEmail(userResponse.getUser().getEmail());
        profile.setUser(user);

        User save1 = userRepository.save(user);
        Profile save = profileRepository.save(profile);

        return ResponseEntity.ok(new UserResponse(userMapper.toDto(save1)));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NoResourceFoundException("user not found"));
        return org.springframework.security.core.userdetails.User.builder().build();
    }
}
