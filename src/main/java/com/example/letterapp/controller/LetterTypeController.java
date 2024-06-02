package com.example.letterapp.controller;


import com.example.letterapp.model.LetterType;
import com.example.letterapp.dto.LetterTypeDto;
import com.example.letterapp.model.User;
import com.example.letterapp.service.LetterTypeService;
import com.example.letterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
I-wish-001(받고 싶은 편지 선택하기 화면)
 */
@RestController // html호출을 위해 수정
@RequestMapping("/api/lettertype")
//@RequestMapping("/")
public class LetterTypeController {

    private final LetterTypeService letterTypeService;
    private final UserService userService;

    @Autowired
    public LetterTypeController(LetterTypeService letterTypeService, UserService userService) {
        this.letterTypeService = letterTypeService;
        this.userService = userService;
    }

    /*
     *   유저의 이메일 전송 여부, 랜덤여부 확인
     *   idx_user 값을 받음
     *   해당 유저의<User-사용자> 테이블에 있는 내용을 다 응답으로 줄 것(JSON 타입)
     */

    // user 엔터티 전체를 JSON타입으로 주는 코드
    @GetMapping("/user/{idx_user}/select")
    public User getUserSelect(@PathVariable("idx_user") Long idx_user) {
        return userService.getUserById(idx_user);
    }

//    // user 엔터티에서 idx_user, email_sub, random_sub 3가지만 JSON타입으로 주는 코드
//    @GetMapping("/user/{idx_user}/select")
//    public Map<String, Object> getUserSelect(@PathVariable("idx_user") Long idx_user) {
//        User user = userService.getUserById(idx_user);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("idx_user", user.getIdx_user());
//        response.put("email_sub", user.isEmail_sub());
//        response.put("random_sub", user.isRandom_sub());
//
//        return response;
//    }

    /*
     * 유저의 편지 유형 선택 정보
     * idx_user 값을 받음
     * LetterType 엔터티내용 전체를 응답으로 주기(JSON 타입)
     */

    @GetMapping("/user/{idx_user}/lettertype")
    public List<LetterType> getLetterTypesForUser(@PathVariable("idx_user") Long idx_user) {
        return letterTypeService.getLetterTypesForUser(idx_user);
    }

    /*
        받고 싶은 편지 선택하기 정보 변경 (저장하기 버튼 클릭시)
        idx_user, email_sub, email, random_sub, idx_letterType, category, comment를 입력 받아와서 갱신
     */
    @PatchMapping("/change/{letterTypeId}")
    public ResponseEntity<LetterType> updateLetterType(@PathVariable("letterTypeId") Long letterTypeId, @RequestBody LetterTypeDto letterTypeDto) {
        try {
            // 주어진 letterTypeId로 LetterType 엔티티를 가져옴
            Optional<LetterType> optionalLetterType = letterTypeService.getLetterTypeById(letterTypeId);

            // letterTypeId에 해당하는 LetterType이 없는 경우
            if (!optionalLetterType.isPresent()) {
                return ResponseEntity.notFound().build(); // 404 Not Found
            }

            LetterType letterType = optionalLetterType.get();

            // User 엔티티도 업데이트
            User user = userService.getUserById(letterTypeDto.getUserIdx());
            if (user == null) {
                return ResponseEntity.badRequest().body(null); // User가 없을 경우 예외 처리
            }
            user.setEmail_sub(letterTypeDto.isEmailSub());
            user.setEmail(letterTypeDto.getEmail());
            user.setRandom_sub(letterTypeDto.isRandomSub());

            // User 엔티티 저장
            userService.saveUser(user);

            // LetterTypeDto에서 필요한 필드로 LetterType 엔티티를 업데이트
            if (letterTypeDto.getCategory() != null) {
                letterType.setCategory(letterTypeDto.getCategory());
            }
            if (letterTypeDto.getComment() != null) {
                letterType.setComment(letterTypeDto.getComment());
            }
            if (letterTypeDto.getDateRecieve() != null) {
                letterType.setDate_recieve(letterTypeDto.getDateRecieve());
            }

            // LetterType 엔티티 저장
            LetterType updatedLetterType = letterTypeService.saveLetterType(letterType);

            // 업데이트된 LetterType 반환
            return ResponseEntity.ok(updatedLetterType); // 200 OK
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }
/*
 * 요청편지 리스트
 */
    @GetMapping("/want")
    public List<Map<String, Object>> getAllLetterTypesWithUserDetails() {
        return letterTypeService.getAllLetterTypesWithUserDetails();
    }
}
