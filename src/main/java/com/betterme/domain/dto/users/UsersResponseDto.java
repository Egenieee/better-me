package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class UsersResponseDto {

    private final Long usersId;
    private final String usersName;
    private final String nickname;
    private final String email;
    private final String slogan;
    private final String createdDate;

    @Builder
    public UsersResponseDto(Users users) {
        this.usersId = users.getId();
        this.usersName = users.getUsersName();
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.slogan = users.getSlogan();
        this.createdDate = users.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }
}
