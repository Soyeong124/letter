package com.example.letterapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.letterapp.model.Letter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    // 편지 상세
    List<Letter> findByRecipient(String recipient);

    @Query("SELECT l FROM Letter l JOIN l.letterType lt WHERE lt.user.idx_user = :userId")
    List<Letter> findLettersByUserId(@Param("userId") Long userId);

    // 전체 편지 수 계산을 위해 추가
    long count();
}
