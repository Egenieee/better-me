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

            requestDto.setUserName(userName);
            requestDto.setNickname(userNickname);
            requestDto.setEmail(userEmail);
            requestDto.setSlogan(userSlogan);
            requestDto.setPassword(userPassword);

            // when, then
            assertEquals(userName, requestDto.getUserName());
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

            requestDto.setUserName(userName);
            requestDto.setNickname(userNickname);
            requestDto.setEmail(userEmail);
            requestDto.setSlogan(userSlogan);
            requestDto.setPassword(userPassword);

            // when
            String encodedPassword = "encodedPassword";
            Users users = requestDto.toEntity(encodedPassword);

            // then
            assertEquals(userName, users.getUserName());
            assertEquals(userNickname, users.getNickname());
            assertEquals(userEmail, users.getEmail());
            assertEquals(userSlogan, users.getSlogan());
            assertEquals(encodedPassword, users.getPassword());
        }
    }
}
