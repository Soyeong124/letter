package com.example.letterapp.controller;

import com.example.letterapp.model.Letter;
import com.example.letterapp.model.User;
import com.example.letterapp.service.LetterService;
import com.example.letterapp.service.LetterTypeService;
import com.example.letterapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Letter Controller", description = "Operations related to letters")
@Controller
public class LetterController {

    @Autowired
    private LetterService letterService;

    @Autowired
    private LetterTypeService letterTypeService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Show letter info by ID")
    // 수정안함
    @GetMapping("/letterinfo")
    public String showLetterInfo(@RequestParam Long letterIdx, Model model) {
        Letter letter = letterService.findLetterById(letterIdx);
        model.addAttribute("letter", letter);
        return "letterinfo";
    }

    @Operation(summary = "Send a new letter")
    @PostMapping("/send")
    public String sendLetter(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "sender") String sender,
            @RequestParam(name = "recipient") String recipient,
            @RequestParam(name = "letterTypeId") Long letterTypeId,
            Model model) {
        Letter letter = new Letter();
        letter.setTitle(title);
        letter.setContent(content);
        letter.setSender(sender);
        letter.setRecipient(recipient);
        letter.setLetterType(letterTypeService.findLetterTypeById(letterTypeId));
        letterService.saveLetter(letter);
        model.addAttribute("status", "편지가 성공적으로 전송되었습니다");
        return "redirect:/";
    }

//    // t실행 성공
//    @GetMapping("/letters")
//    public String showLettersPage(Model model) {
//        model.addAttribute("letters", letterService.findAllLetters());
//        return "letters";
//    }

    @Operation(summary = "Show letters page for the authenticated user")
    @GetMapping("/letters")
    public String showLettersPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userService.findByNickname(userDetails.getUsername());
            if (user != null) {
                List<Letter> letters = letterService.findLettersByUserId(user.getIdx_user());
                model.addAttribute("letters", letters);
            }
        }
        return "letters";
    }

    @Operation(summary = "Show letter content by ID")
    @GetMapping("/letters/{id}")
    public String showLetterPage(@PathVariable Long id, Model model) {
        Letter letter = letterService.findLetterById(id);
        if (letter == null) {
            return "error"; // 편지를 찾지 못한 경우 에러 페이지로 이동
        }
        model.addAttribute("letter", letter);
        // 페이지 주소 변경
        return "letterContent";
    }

    @Operation(summary = "Show index page")
    @GetMapping("/index")
    public String showIndexPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            User user = userService.findByNickname(userDetails.getUsername());
            if (user != null) {
                List<Letter> letters = letterService.findLettersByRecipient(user.getNickname());
                model.addAttribute("letters", letters);
            }
        }
        long totalLetters = letterService.countLetters(); // 전체 편지 수 계산
        model.addAttribute("totalLetters", totalLetters); // 모델에 추가
        return "index"; // 인덱스 페이지로 이동
    }
}
