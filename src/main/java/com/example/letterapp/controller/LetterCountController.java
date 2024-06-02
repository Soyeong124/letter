package com.example.letterapp.controller;

import com.example.letterapp.service.LetterCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LetterCountController {
    private final LetterCountService letterCountService;

    public LetterCountController(LetterCountService letterCountService) {
        this.letterCountService = letterCountService;
    }

    @GetMapping("/count")
    public Map<String, Long> getLetterCount() {
        long count = letterCountService.countLetters();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return response;
    }
}
