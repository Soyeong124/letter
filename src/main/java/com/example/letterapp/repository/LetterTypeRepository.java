package com.example.letterapp.repository;

import com.example.letterapp.model.LetterType;
import com.example.letterapp.model.User;
import com.example.letterapp.service.LetterTypeService;
import com.example.letterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Repository
public interface LetterTypeRepository extends JpaRepository<LetterType, Long> {
    List<LetterType> findByUser(User user);

    @Query("SELECT new map(lt.idx_letterType as idx_letterType, lt.category as category, u.nickname as nickname, lt.comment as comment, lt.date_recieve as date_receive, u.idx_user as idx_user) " +
            "FROM LetterType lt JOIN lt.user u")
    List<Map<String, Object>> findAllLetterTypesWithUserDetails();

}