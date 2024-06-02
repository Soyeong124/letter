package com.example.letterapp.service;

import com.example.letterapp.model.LetterType;
import com.example.letterapp.model.User;
import com.example.letterapp.repository.LetterTypeRepository;
import com.example.letterapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LetterTypeService {

    private final LetterTypeRepository letterTypeRepository;
    private final UserRepository userRepository;

    @Autowired
    public LetterTypeService(LetterTypeRepository letterTypeRepository, UserRepository userRepository) {
        this.letterTypeRepository = letterTypeRepository;
        this.userRepository = userRepository;
    }

    public User getUserById(Long idx_user) {
        return userRepository.findById(idx_user).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<LetterType> getLetterTypesForUser(Long idx_user) {
        User user = userRepository.findById(idx_user).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return letterTypeRepository.findByUser(user);
    }

    public LetterType saveLetterType(LetterType letterType) {
        return letterTypeRepository.save(letterType);
    }

    //  테스트 때문에 잠깐 추가
    public List<LetterType> findAllLetterTypes() {
        return letterTypeRepository.findAll();
    }

    public LetterType findLetterTypeById(Long id) {
        return letterTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Letter type not found"));
    }

    public Optional<LetterType> getLetterTypeById(Long letterTypeId) {
        return letterTypeRepository.findById(letterTypeId);
    }

    public List<Map<String, Object>> getAllLetterTypesWithUserDetails() {
        return letterTypeRepository.findAllLetterTypesWithUserDetails();
    }
}
