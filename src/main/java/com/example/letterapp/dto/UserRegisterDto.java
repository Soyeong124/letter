package com.example.letterapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDto {
    private String nickname;
    private String password;
    private String confirmPassword;
}
