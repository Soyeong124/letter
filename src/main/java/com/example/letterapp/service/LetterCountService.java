package com.example.letterapp.service;

import com.example.letterapp.repository.LetterRepository;
import org.springframework.stereotype.Service;

@Service
public class LetterCountService {
    private final LetterRepository letterRepository;

    public LetterCountService(LetterRepository letterRepository) {
        this.letterRepository = letterRepository;
    }

    public long countLetters() {
        return letterRepository.count();
    }
    }