package com.betterme.service;

import com.betterme.domain.dto.users.UsersResponseDto;
import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.domain.entity.Users;
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

    private Users findUsersWithUserName(String username) {
        return usersRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. user name = " + username));
    }

    @Transactional
    public Long save(UsersSaveRequestDto requestDto) {
        // 사용자 아이디 중복 체크
        if (isExistUserName(requestDto.getUserName())) {
            throw new UsersNotUniqueException("이미 등록된 사용자 입니다.");
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 저장
        return usersRepository.save(requestDto.toEntity(encodedPassword)).getId();
    }

    private boolean isExistUserName(String userName) {
        return usersRepository.existsUsersByUserName(userName);
    }

    public UsersResponseDto findByUserName(String username) {
        Users users = findUsersWithUserName(username);

        return new UsersResponseDto(users);
    }

}
