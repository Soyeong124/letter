package com.example.letterapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.letterapp.model.User;
import com.example.letterapp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        User appUser = userRepository.findByNickname(nickname);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // UserBuilder를 사용하여 UserDetails 객체를 생성합니다.
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(appUser.getNickname());
        builder.password(appUser.getPassword());
        builder.roles("USER");

        return builder.build();
    }
}
