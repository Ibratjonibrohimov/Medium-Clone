package uz.najottalim.javan6.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.najottalim.javan6.dto.userdto.UserDto;
import uz.najottalim.javan6.dto.userdto.UserResponse;
import uz.najottalim.javan6.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/users/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserResponse userResponse){
        return userService.loginUser(userResponse);
    }
    @PostMapping("/users")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserResponse userResponse){
        return userService.registerUser(userResponse);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UserResponse userResponse){
        return userService.updateUser(userResponse);
    }
}
