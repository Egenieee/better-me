package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersResponseDto {

    private final Long userId;
    private final String userName;
    private final String email;
    private final String slogan;

    @Builder
    public UsersResponseDto(Users users) {
        this.userId = users.getId();
        this.userName = users.getUserName();
        this.email = users.getEmail();
        this.slogan = users.getSlogan();
    }
}
