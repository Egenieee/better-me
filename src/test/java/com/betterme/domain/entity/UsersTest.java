package com.betterme.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {

    private final String userName = "testName";
    private final String userNickname = "testNickname";
    private final String userEmail = "test@better.me";
    private final String userPassword = "testPassword";
    private final String userSlogan = "testSlogan";

    @Test
    @DisplayName("회원 객체가 생성된다")
    public void createUsers() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        // when, then
        assertEquals(userName, users.getUsersName());
        assertEquals(userNickname, users.getNickname());
        assertEquals(userEmail, users.getEmail());
        assertEquals(userSlogan, users.getSlogan());
        assertEquals(userPassword, users.getPassword());
    }

    @Test
    @DisplayName("회원 객체가 수정된다")
    public void updateUsers() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        // when
        users.update("changedNickname", "changedEmail", "changedSlogan");

        // then
        assertEquals("changedNickname", users.getNickname());
        assertEquals("changedEmail", users.getEmail());
        assertEquals("changedSlogan", users.getSlogan());
    }
}
