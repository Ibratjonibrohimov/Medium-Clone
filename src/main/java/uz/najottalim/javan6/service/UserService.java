package uz.najottalim.javan6.service;

import org.springframework.http.ResponseEntity;
import uz.najottalim.javan6.dto.userdto.UserResponse;

public interface UserService {
    ResponseEntity<UserResponse> loginUser(UserResponse userResponse);

    ResponseEntity<UserResponse> registerUser(UserResponse userResponse);

    ResponseEntity<UserResponse> getCurrentUser();

    ResponseEntity<UserResponse> updateUser(UserResponse userResponse);
}
