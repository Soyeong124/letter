package com.example.letterapp.service;

import com.example.letterapp.model.Letter;
import com.example.letterapp.model.LetterType;
import com.example.letterapp.repository.LetterRepository;
import com.example.letterapp.repository.LetterTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LetterService {

    @Autowired
    private LetterRepository letterRepository;

    @Autowired
    private LetterTypeRepository letterTypeRepository;

    public void saveLetter(Letter letter) {
        letterRepository.save(letter);
    }

    public List<Letter> findAllLetters() {
        return letterRepository.findAll();
    }

    public Letter findLetterById(Long id) {
        return letterRepository.findById(id).orElse(null);
    }

    // 추가
    public List<Letter> findLettersByRecipient(String recipient) {
        return letterRepository.findByRecipient(recipient);
    }

    // 사용자 ID로 편지 조회하는 메서드 추가
    public List<Letter> findLettersByUserId(Long userId) {
        return letterRepository.findLettersByUserId(userId);
    }

//    public LetterType findLetterTypeById(Long id) {
//        return letterTypeRepository.findById(id).orElse(null);
//    }

    public List<LetterType> findAllLetterTypes() {
        return letterTypeRepository.findAll();
    }

    // 편지 리스트
    public List<Letter> getLettersWithComments() {
        return letterRepository.findAll();
    }

    // 편지 내용
    public String getLetterContentById(Long id) {
        Letter letter = letterRepository.findById(id).orElse(null);
        if (letter == null) {
            return null; // 해당 ID의 편지가 없을 경우
        }
        return letter.getContent(); // 편지의 내용 반환
    }


    // countLetters 새로운 메서드 추가: 전체 편지 수 계산
    public long countLetters() {
        return letterRepository.count();
    }


}
