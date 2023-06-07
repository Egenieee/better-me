package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class UsersUpdateRequestDto {

    private final Long usersId;
    private final String usersName;

    @Length(min = 1, max = 10, message = "사용자 닉네임은 1 ~ 10 길이로 입력해야 합니다.")
    private final String nickname;

    private final String slogan;

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "email은 공백일 수 없습니다.")
    private final String email;

    @Builder
    public UsersUpdateRequestDto(Users users) {
        this.usersId = users.getId();
        this.nickname = users.getNickname();
        this.usersName = users.getUsersName();
        this.slogan = users.getSlogan();
        this.email = users.getEmail();
    }
}
