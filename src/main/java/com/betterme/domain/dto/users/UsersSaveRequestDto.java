package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class UsersSaveRequestDto {

    @Length(min = 6, max = 15, message = "6 ~ 15 길이로 입력해야 합니다.")
    private String userName;

    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank
    private String email;

    private String slogan;

    @Length(min = 6, max = 15, message = "6 ~ 15 길이로 입력해야 합니다.")
    private String password;

    @Builder
    public UsersSaveRequestDto(String userName, String email, String slogan, String password) {
        this.userName = userName;
        this.email = email;
        this.slogan = slogan;
        this.password = password;
    }

    public Users toEntity(String encodedPassword) {
        return Users.builder()
                .name(userName)
                .email(email)
                .password(encodedPassword)
                .slogan(slogan)
                .build();
    }
}
