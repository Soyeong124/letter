package com.example.letterapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // HTML 페이지 반환을 위해 사용
public class HtmlController {

    @GetMapping("/select")
    public String selectLetterTypePage() {
        return "select"; // Thymeleaf 템플릿 파일 이름 (select.html)
    }

    @GetMapping("/write")
    public String writeLetterPage() {
        return "write"; // Thymeleaf 템플릿 파일 이름 (write.html)
    }
}
