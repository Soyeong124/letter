package com.example.letterapp.controller;

import com.example.letterapp.model.Letter;
import com.example.letterapp.service.LetterService;
import com.example.letterapp.service.LetterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class LetterDetails {

    @Autowired
    private LetterService letterService;

    @Autowired
    private LetterTypeService letterTypeService;


    // 편지 목록을 가져오는 메서드
    @GetMapping("/letterDetails")
    public String getLetterDetails(Model model, Principal principal) {
        List<Letter> letters = letterService.getLettersWithComments();
        model.addAttribute("letters", letters);
        return "letterDetails";
    }


    // 특정 편지의 내용을 가져오는 메서드
    @GetMapping("/letterDetails/{id}")
    public String getLetterContentById(@PathVariable("id") Long id, Model model) {
        Letter letter = letterService.findLetterById(id);
        if (letter == null) {
            return "error"; // 편지를 찾지 못한 경우 에러 페이지로 이동
        }
        model.addAttribute("letter", letter);
        return "letterContent";
    }


}
