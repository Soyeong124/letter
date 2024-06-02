package com.example.letterapp.controller;

import com.example.letterapp.dto.UserRegisterDto;
import com.example.letterapp.service.LetterService;
import com.example.letterapp.model.User;
import com.example.letterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private LetterService letterService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, UserRegisterDto userRegisterDto
    ) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            // 비밀번호 일치 error 문구 노출을 위해 수정
            model.addAttribute("passwordError", "비밀번호가 일치하지 않습니다.");
            return "register";
        }

        // 닉네임 중복 여부 확인 함수 추가
        if (userService.isNicknameExists(userRegisterDto.getNickname())) {
            model.addAttribute("nicknameError", "중복된 닉네임입니다.");
            return "register";
        }

        userService.registerUser(userRegisterDto.getNickname(), userRegisterDto.getPassword());
        return "redirect:/login";
    }

    // /맵핑 및 인증 여부를 모델에 전달하여 화면에서 이를 사용할 수 있도록 수정
    // 홈에서 바로 건수 확인할 수 있도록 수정
    // AuthController
    @GetMapping("/")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            model.addAttribute("username", authentication.getName());
        }
        // 전체 편지 수를 모델에 추가하는 로직을 여기에 추가하실 수도 있습니다.
        long totalLetters = letterService.countLetters();
        model.addAttribute("totalLetters", totalLetters);
        return "index";
    }
}
