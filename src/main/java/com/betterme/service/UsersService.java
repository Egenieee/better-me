package com.betterme.service;

import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Transactional
    public void save(UsersSaveRequestDto requestDto) {
        usersRepository.save(requestDto.toEntity());
    }
}
