package com.betterme.domain.dto.users;

import com.betterme.domain.entity.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersDtoTest {

    private final String userName = "testName";
    private final String userNickname = "testNickname";
    private final String userEmail = "test@better.me";
    private final String userPassword = "testPassword";
    private final String userSlogan = "testSlogan";

    @Nested
    class UsersSaveRequestDtoTest {

        @Test
        @DisplayName("UsersSaveRequestDto가 생성된다")
        public void createUsersSaveRequestDto() {
            // given
            UsersSaveRequestDto requestDto = new UsersSaveRequestDto();

            requestDto.setUsersName(userName);
            requestDto.setNickname(userNickname);
            requestDto.setEmail(userEmail);
            requestDto.setSlogan(userSlogan);
            requestDto.setPassword(userPassword);

            // when, then
            assertEquals(userName, requestDto.getUsersName());
            assertEquals(userNickname, requestDto.getNickname());
            assertEquals(userEmail, requestDto.getEmail());
            assertEquals(userSlogan, requestDto.getSlogan());
            assertEquals(userPassword, requestDto.getPassword());
        }

        @Test
        @DisplayName("UsersSaveRequestDto에서 Users로 변환된다")
        public void convertToRequestDtoToEntity() {
            // given
            UsersSaveRequestDto requestDto = new UsersSaveRequestDto();

            requestDto.setUsersName(userName);
            requestDto.setNickname(userNickname);
            requestDto.setEmail(userEmail);
            requestDto.setSlogan(userSlogan);
            requestDto.setPassword(userPassword);

            // when
            String encodedPassword = "encodedPassword";
            Users users = requestDto.toEntity(encodedPassword);

            // then
            assertEquals(userName, users.getUsersName());
            assertEquals(userNickname, users.getNickname());
            assertEquals(userEmail, users.getEmail());
            assertEquals(userSlogan, users.getSlogan());
            assertEquals(encodedPassword, users.getPassword());
        }
    }

    @Nested
    class UsersUpdateRequestDtoTest {

        @Test
        @DisplayName("UsersUpdateRequestDto가 만들어진다")
        public void createUsersRequestDto() {
            // given
            Users users = Users.builder()
                    .name(userName)
                    .nickname(userNickname)
                    .email(userEmail)
                    .slogan(userSlogan)
                    .password(userPassword)
                    .build();

            // when
            UsersUpdateRequestDto requestDto = UsersUpdateRequestDto.builder()
                    .usersId(users.getId())
                    .nickname(users.getNickname())
                    .usersName(users.getUsersName())
                    .slogan(users.getSlogan())
                    .email(users.getEmail())
                    .build();

            // then
            assertEquals(users.getUsersName(), requestDto.getUsersName());
            assertEquals(users.getNickname(), requestDto.getNickname());
            assertEquals(users.getEmail(), requestDto.getEmail());
            assertEquals(users.getSlogan(), requestDto.getSlogan());
        }
    }

    @Nested
    class UsersResponseDtoTest {

        @Test
        @DisplayName("UsersResponseDto가 만들어진다")
        public void createUsersResponseDto() {
            // given
            Users users = Users.builder()
                    .name(userName)
                    .nickname(userNickname)
                    .email(userEmail)
                    .slogan(userSlogan)
                    .password(userPassword)
                    .build();

            // when
            UsersResponseDto responseDto = UsersResponseDto.builder()
                    .users(users)
                    .build();

            // then
            assertEquals(users.getUsersName(), responseDto.getUsersName());
            assertEquals(users.getNickname(), responseDto.getNickname());
            assertEquals(users.getEmail(), responseDto.getEmail());
            assertEquals(users.getSlogan(), responseDto.getSlogan());
        }
    }
}
