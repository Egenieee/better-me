package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UsersSaveRequestDto {

    @Length(min = 6, max = 15, message = "사용자 ID는 6 ~ 15 길이로 입력해야 합니다.")
    private String usersName;

    @Length(min = 1, max = 10, message = "사용자 닉네임은 1 ~ 10 길이로 입력해야 합니다.")
    private String nickname;

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "email은 공백일 수 없습니다.")
    private String email;

    private String slogan;

    @Length(min = 6, max = 15, message = "비밀번호는 6 ~ 15 길이로 입력해야 합니다.")
    private String password;

    public Users toEntity(String encodedPassword) {
        return Users.builder()
                .name(usersName)
                .nickname(nickname)
                .email(email)
                .password(encodedPassword)
                .slogan(slogan)
                .build();
    }
}
