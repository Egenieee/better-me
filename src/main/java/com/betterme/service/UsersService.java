package com.betterme.service;

import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.exception.UsersNotUniqueException;
import com.betterme.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(UsersSaveRequestDto requestDto) {
        // 사용자 아이디 중복 체크
        if (isExistUserName(requestDto.getUserName())) {
            throw new UsersNotUniqueException("이미 등록된 사용자 입니다.");
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 저장
        usersRepository.save(requestDto.toEntity(encodedPassword));
    }

    private boolean isExistUserName(String userName) {
        return usersRepository.existsUsersByUserName(userName);
    }
}
