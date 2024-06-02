package com.example.letterapp.controller;

import com.example.letterapp.model.User;
import com.example.letterapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
public class UserController {

    private final UserService userService;

    // UserService를 주입받는 생성자
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get logged-in user's Idx")
    @GetMapping("/api/user/idx")
    public Long getLoggedInUserId(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User user = userService.findByNickname(userDetails.getUsername());
            if (user != null) {
                return user.getIdx_user(); // 로그인한 사용자의 Idx 반환
            }
        }
        return null; // 사용자가 로그인하지 않았거나 사용자를 찾을 수 없는 경우
    }
}
