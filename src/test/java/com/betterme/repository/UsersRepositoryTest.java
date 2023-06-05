package com.betterme.repository;

import com.betterme.domain.entity.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private final String userName = "testName";
    private final String userNickname = "testNickname";
    private final String userEmail = "test@better.me";
    private final String userPassword = "testPassword";
    private final String userSlogan = "testSlogan";

    @AfterEach
    public void deleteAll() {
        usersRepository.deleteAll();
    }

    @Test
    @DisplayName("회원이 저장된다")
    public void saveUsers() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        // when
        usersRepository.save(users);

        Users savedUsers = usersRepository.findAll().get(0);

        // then
        assertEquals(userName, savedUsers.getUserName());
        assertEquals(userNickname, savedUsers.getNickname());
        assertEquals(userEmail, savedUsers.getEmail());
        assertEquals(userSlogan, savedUsers.getSlogan());
    }

    @Test
    @DisplayName("회원이 Id 값으로 조회된다")
    public void findByUserId() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        Long savedUserId = usersRepository.save(users).getId();

        // when
        Users findUsers = usersRepository.findById(savedUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // then
        assertEquals(users.getUserName(), findUsers.getUserName());
        assertEquals(users.getNickname(), findUsers.getNickname());
        assertEquals(users.getEmail(), findUsers.getEmail());
        assertEquals(users.getSlogan(), findUsers.getSlogan());
    }

    @Test
    @DisplayName("회원이 회원 이름으로 조회된다")
    public void findByUserName() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        usersRepository.save(users);

        // when
        Users findUsers = usersRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

        // then
        assertEquals(users.getUserName(), findUsers.getUserName());
        assertEquals(users.getNickname(), findUsers.getNickname());
        assertEquals(users.getEmail(), findUsers.getEmail());
        assertEquals(users.getSlogan(), findUsers.getSlogan());
    }

    @Test
    @DisplayName("회원의 이름으로 존재 여부를 반환한다: true일 경우")
    public void isRedundantUserNameWithTrueCasee() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        usersRepository.save(users);

        // when
        boolean isRedundant = usersRepository.existsUsersByUserName(userName);

        // then
        assertTrue(isRedundant);
    }

    @Test
    @DisplayName("회원의 이름으로 존재 여부를 반환한다: false일 경우")
    public void isRedundantUserNameWithFalseCase() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        usersRepository.save(users);

        // when
        boolean isRedundant = usersRepository.existsUsersByUserName("otherUserName");

        // then
        assertFalse(isRedundant);
    }

    @Test
    @DisplayName("회원이 삭제된다")
    public void deleteUsers() {
        // given
        Users users = Users.builder()
                .name(userName)
                .nickname(userNickname)
                .email(userEmail)
                .slogan(userSlogan)
                .password(userPassword)
                .build();

        usersRepository.save(users);

        // when
        usersRepository.delete(users);

        // then
        assertEquals(0, usersRepository.findAll().size());
    }
}
