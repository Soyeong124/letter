package com.example.letterapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.letterapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
}
