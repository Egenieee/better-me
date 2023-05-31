package com.betterme.domain.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersUpdateRequestDto {

    private final Long userId;

    private final String userName;
    private final String slogan;

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "email은 공백일 수 없습니다.")
    private final String email;

    @Builder
    public UsersUpdateRequestDto(Long userId, String userName, String slogan, String email) {
        this.userId = userId;
        this.userName = userName;
        this.slogan = slogan;
        this.email = email;
    }
}
