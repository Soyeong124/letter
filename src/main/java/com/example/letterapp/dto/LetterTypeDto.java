package com.example.letterapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class LetterTypeDto {
    private Long userIdx;
    private boolean emailSub;
    private String email;
    private boolean randomSub;
    private List<Integer> category;
    private String comment;
    private LocalDateTime dateRecieve;
}
