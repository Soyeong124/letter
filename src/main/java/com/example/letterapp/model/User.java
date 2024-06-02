package com.example.letterapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx_user;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    private boolean email_sub;

    @Column(nullable = true)
    private String email;

    private boolean random_sub;

    @OneToMany(mappedBy = "sender")
    private Set<Letter> sentLetters;

    @OneToMany(mappedBy = "recipient")
    private Set<Letter> receivedLetters;

    @JsonIgnore
//    @OneToMany(mappedBy = "user")
//    private Set<LetterType> letterTypes;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<LetterType> letterTypes;

    // Getters and Setters
//
//    public Long getIdx_user() {
//        return idx_user;
//    }
//
//    public void setIdx_user(Long idx_user) {
//        this.idx_user = idx_user;
//    }
//
//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public boolean isEmail_sub() {
//        return email_sub;
//    }
//
//    public void setEmail_sub(boolean email_sub) {
//        this.email_sub = email_sub;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public boolean isRandom_sub() {
//        return random_sub;
//    }
//
//    public void setRandom_sub(boolean random_sub) {
//        this.random_sub = random_sub;
//    }
//
//    public Set<Letter> getSentLetters() {
//        return sentLetters;
//    }
//
//    public void setSentLetters(Set<Letter> sentLetters) {
//        this.sentLetters = sentLetters;
//    }
//
//    public Set<Letter> getReceivedLetters() {
//        return receivedLetters;
//    }
//
//    public void setReceivedLetters(Set<Letter> receivedLetters) {
//        this.receivedLetters = receivedLetters;
//    }
//
//    public Set<LetterType> getLetterTypes() {
//        return letterTypes;
//    }
//
//    public void setLetterTypes(Set<LetterType> letterTypes) {
//        this.letterTypes = letterTypes;
//    }
}