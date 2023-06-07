package com.betterme.service;

import com.betterme.domain.dto.users.UsersResponseDto;
import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.domain.dto.users.UsersUpdateRequestDto;
import com.betterme.domain.entity.Users;
import com.betterme.repository.UsersRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class UsersServiceTest {

    UsersService usersService;

    @MockBean
    UsersRepository usersRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    private final String usersName = "testUserName";
    private final String usersEmail = "test@betterme.com";
    private final String usersNickname = "testNickname";
    private final String usersSlogan = "testSlogan";
    private final String usersPassword = "testPassword";

    private final String encodedPassword = "BEtTeRmE";

    @BeforeEach
    public void setUp() {
        usersService = new UsersService(usersRepository, passwordEncoder);
    }

    @AfterEach
    public void deleteAll() {
        usersRepository.deleteAll();
    }

    public Users getUsers() {
        return Users.builder()
                .name(usersName)
                .email(usersEmail)
                .nickname(usersNickname)
                .slogan(usersSlogan)
                .password(encodedPassword)
                .build();
    }

    @Nested
    class SuccessCases {

        @Test
        @DisplayName("UsersSaveRequestDto가 주어졌을 때, 회원이 저장된다")
        public void saveUsers() {
            // given
            UsersSaveRequestDto requestDto = new UsersSaveRequestDto();

            requestDto.setUsersName(usersName);
            requestDto.setNickname(usersNickname);
            requestDto.setEmail(usersEmail);
            requestDto.setSlogan(usersSlogan);
            requestDto.setPassword(usersPassword);

            Users users = requestDto.toEntity(encodedPassword);

            ReflectionTestUtils.setField(users, "id", 1L);

            Mockito.when(usersRepository.existsUsersByUsersName(usersName)).thenReturn(false);
            Mockito.when(passwordEncoder.encode(usersPassword)).thenReturn(encodedPassword);
            Mockito.when(usersRepository.save(Mockito.any(Users.class))).thenReturn(users);

            // when
            Long saveUsersId = usersService.save(requestDto);

            // then
            assertEquals(1L, saveUsersId);
        }

        @Test
        @DisplayName("회원의 아이디로 회원 정보 찾아 UsersResponseDto로 반환된다")
        public void findByUserName() {
            // given
            Users users = getUsers();

            ReflectionTestUtils.setField(users, "id", 2L);

            Mockito.when(usersRepository.save(Mockito.any(Users.class))).thenReturn(users);
            Mockito.when(usersRepository.findByUsersName(usersName)).thenReturn(Optional.of(users));

            usersRepository.save(users);

            // when
            UsersResponseDto responseDto = usersService.findByUsersName(usersName);

            // then
            assertEquals(2L, responseDto.getUsersId());
            assertEquals(usersName, responseDto.getUsersName());
            assertEquals(usersEmail, responseDto.getEmail());
            assertEquals(usersSlogan, responseDto.getSlogan());
        }

        @Test
        @DisplayName("회원 정보 수정 폼이 생성된다")
        public void createUpdateRequestDto() {
            // given
            Users users = getUsers();

            ReflectionTestUtils.setField(users, "id", 3L);

            Mockito.when(usersRepository.save(Mockito.any(Users.class))).thenReturn(users);
            Mockito.when(usersRepository.findById(users.getId())).thenReturn(Optional.of(users));

            usersRepository.save(users);

            // when
            UsersUpdateRequestDto requestDto = usersService.getUpdateRequestDto(users.getId());

            // then
            assertEquals(3L, requestDto.getUsersId());
            assertEquals(usersName, requestDto.getUsersName());
            assertEquals(usersEmail, requestDto.getEmail());
            assertEquals(usersSlogan, requestDto.getSlogan());
        }

        @Test
        @DisplayName("회원의 정보가 수정된다")
        public void updateUsers() {
            // given
            Users users = getUsers();

            ReflectionTestUtils.setField(users, "id", 4L);

            Mockito.when(usersRepository.save(Mockito.any(Users.class))).thenReturn(users);
            Mockito.when(usersRepository.findById(users.getId())).thenReturn(Optional.of(users));
            Mockito.when(usersRepository.findByUsersName(users.getUsersName())).thenReturn(Optional.of(users));

            usersRepository.save(users);

            UsersUpdateRequestDto requestDto = UsersUpdateRequestDto.builder()
                    .usersId(users.getId())
                    .nickname(users.getNickname())
                    .usersName(users.getUsersName())
                    .slogan(users.getSlogan())
                    .email(users.getEmail())
                    .build();

            String changedNickname = "changedNickname";

            ReflectionTestUtils.setField(requestDto, "nickname", changedNickname);

            // when
            usersService.update(users.getId(), requestDto);

            // then
            UsersResponseDto responseDto = usersService.findByUsersName(usersName);

            assertEquals(usersName, responseDto.getUsersName());
            assertEquals(usersEmail, responseDto.getEmail());
            assertEquals(changedNickname, responseDto.getNickname());
            assertEquals(usersSlogan, responseDto.getSlogan());
        }

        @Test
        @DisplayName("회원의 정보가 삭제된다")
        public void deleteUsers() {
            // given
            Users users = getUsers();

            ReflectionTestUtils.setField(users, "id", 5L);

            Mockito.when(usersRepository.findById(users.getId())).thenReturn(Optional.of(users));
            Mockito.when(usersRepository.findAll()).thenReturn(new ArrayList<>());
            Mockito.doNothing().when(usersRepository).delete(Mockito.any(Users.class));

            // when
            usersService.delete(users.getId());

            // then
            assertEquals(0, usersRepository.findAll().size());
        }
    }

}
