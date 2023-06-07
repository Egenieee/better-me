package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersResponseDto {

    private final Long usersId;
    private final String usersName;
    private final String nickname;
    private final String email;
    private final String slogan;

    @Builder
    public UsersResponseDto(Users users) {
        this.usersId = users.getId();
        this.usersName = users.getUsersName();
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.slogan = users.getSlogan();
    }
}
