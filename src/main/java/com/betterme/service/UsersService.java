package com.betterme.service;

import com.betterme.domain.dto.users.UsersResponseDto;
import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.domain.dto.users.UsersUpdateRequestDto;
import com.betterme.domain.entity.Users;
import com.betterme.exception.UsersNotUniqueException;
import com.betterme.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    private Users findUsersByUsersId(Long usersId) {
        return usersRepository.findById(usersId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. users id = " + usersId));
    }

    private Users findUsersByUsersName(String usersName) {
        return usersRepository.findByUsersName(usersName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다. users name = " + usersName));
    }

    @Transactional
    public Long save(UsersSaveRequestDto requestDto) {
        // 사용자 아이디 중복 체크
        if (isExistUsersName(requestDto.getUsersName())) {
            throw new UsersNotUniqueException("이미 등록된 사용자 입니다.");
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 저장
        Users savedUsers = usersRepository.save(requestDto.toEntity(encodedPassword));

        log.info("Users is saved with users id = " + savedUsers.getId());

        return savedUsers.getId();
    }

    private boolean isExistUsersName(String usersName) {
        return usersRepository.existsUsersByUsersName(usersName);
    }

    public UsersResponseDto findByUsersName(String usersName) {
        Users users = findUsersByUsersName(usersName);

        return new UsersResponseDto(users);
    }

    // 회원 정보 수정 폼 생성
    public UsersUpdateRequestDto getUpdateRequestDto(Long usersId) {
        Users users = findUsersByUsersId(usersId);

        return new UsersUpdateRequestDto(users.getId(), users.getNickname(), users.getUsersName(), users.getSlogan(), users.getEmail());
    }

    @Transactional
    public void update(Long usersId, UsersUpdateRequestDto requestDto) {
        Users users = findUsersByUsersId(usersId);
        users.update(requestDto.getNickname(), requestDto.getEmail(), requestDto.getSlogan());

        log.info("Users is update with users id = " + users.getId());
    }

    @Transactional
    public void delete(Long usersId) {
        Users users = findUsersByUsersId(usersId);
        usersRepository.delete(users);

        log.info("Users is deleted with users id = " + users.getId());
    }

}
