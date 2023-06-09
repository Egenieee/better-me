package com.betterme.integration;

import com.betterme.domain.dto.users.UsersResponseDto;
import com.betterme.domain.entity.Users;
import com.betterme.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UsersIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MockMvc mvc;

    private final String usersName = "testUsersName";
    private final String usersEmail = "test@betterme.com";
    private final String usersNickname = "nickname";
    private final String usersSlogan = "testSlogan";
    private final String usersPassword = "testPassword";


    @AfterEach
    public void deleteAll() {
        usersRepository.deleteAll();
    }

    public UsersResponseDto saveTestUsers() throws Exception {
        String url = "http://localhost:" + port + "/users/new";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("usersName", usersName)
                .param("nickname", usersNickname)
                .param("email", usersEmail)
                .param("slogan", usersSlogan)
                .param("password", usersPassword));

        Users users = usersRepository.findAll().get(0);

        return new UsersResponseDto(users);
    }

    @Nested
    @DisplayName("요청이 성공하는 경우")
    class SuccessCases {

        @Test
        @DisplayName("회원 가입 폼을 생성한다")
        public void getCreateUsersForm() throws Exception {
            // given
            String url = "http://localhost:" + port + "/users/new";

            // when, then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andExpect(view().name("users/createUsersForm"));
        }

        @Test
        @DisplayName("회원을 저장한다")
        public void saveUsers() throws Exception {
            // given
            String url = "http://localhost:" + port + "/users/new";

            // when, then
            mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("usersName", usersName)
                            .param("nickname", usersNickname)
                            .param("email", usersEmail)
                            .param("slogan", usersSlogan)
                            .param("password", usersPassword))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

            List<Users> users = usersRepository.findAll();
            assertEquals(usersName, users.get(0).getUsersName());
            assertEquals(1, users.size());
        }

        @Test
        @DisplayName("회원 로그인 화면이 반환된다")
        public void getLoginView() throws Exception {
            // given
            String url = "http://localhost:" + port + "/users/login";

            // when, then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andExpect(view().name("users/loginForm"));
        }

        @Test
        @WithMockUser(username = usersName, roles = "USERS")
        @DisplayName("회원의 정보가 보여진다")
        public void getUsersInformation() throws Exception {
            // given
            UsersResponseDto responseDto = saveTestUsers();

            String url = "http://localhost:" + port + "/users/information";

            // when, then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andExpect(view().name("users/usersInformation"))
                    .andExpect(model().attributeExists("usersResponseDto"))
                    .andExpect(model().attribute("usersResponseDto", hasProperty("usersId", equalTo(responseDto.getUsersId()))))
                    .andExpect(model().attribute("usersResponseDto", hasProperty("usersName", equalTo(responseDto.getUsersName()))))
                    .andExpect(model().attribute("usersResponseDto", hasProperty("nickname", equalTo(responseDto.getNickname()))))
                    .andExpect(model().attribute("usersResponseDto", hasProperty("email", equalTo(responseDto.getEmail()))))
                    .andExpect(model().attribute("usersResponseDto", hasProperty("slogan", equalTo(responseDto.getSlogan()))));
        }

        @Test
        @DisplayName("회원 정보 수정 폼을 생성한다")
        public void getUpdateUsersForm() throws Exception {
            // given
            UsersResponseDto responseDto = saveTestUsers();

            String url = "http://localhost:" + port + "/users/" + responseDto.getUsersId();

            // when, then
            mvc.perform(get(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    .andExpect(status().isOk())
                    .andExpect(view().name("users/updateUsersForm"))
                    .andExpect(model().attributeExists("usersUpdateRequestDto"))
                    .andExpect(model().attribute("usersUpdateRequestDto", hasProperty("usersId", equalTo(responseDto.getUsersId()))))
                    .andExpect(model().attribute("usersUpdateRequestDto", hasProperty("usersName", equalTo(responseDto.getUsersName()))))
                    .andExpect(model().attribute("usersUpdateRequestDto", hasProperty("nickname", equalTo(responseDto.getNickname()))))
                    .andExpect(model().attribute("usersUpdateRequestDto", hasProperty("email", equalTo(responseDto.getEmail()))))
                    .andExpect(model().attribute("usersUpdateRequestDto", hasProperty("slogan", equalTo(responseDto.getSlogan()))));
        }

        @Test
        @DisplayName("회원 정보가 수정된다")
        public void updateUsers() throws Exception {
            // given
            UsersResponseDto responseDto = saveTestUsers();

            String url = "http://localhost:" + port + "/users/" + responseDto.getUsersId();

            String changedNickname = "changed";

            // when, then
            mvc.perform(put(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("usersId", responseDto.getUsersId().toString())
                            .param("usersName", usersName)
                            .param("nickname", changedNickname)
                            .param("email", usersEmail)
                            .param("slogan", usersSlogan)
                            .param("password", usersPassword))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

            List<Users> users = usersRepository.findAll();
            assertEquals(changedNickname, users.get(0).getNickname());
        }

        @Test
        @DisplayName("회원이 삭제된다")
        public void deleteUsers() throws Exception {
            // given
            UsersResponseDto responseDto = saveTestUsers();

            Long usersId = responseDto.getUsersId();

            String url = "http://localhost:" + port + "/users/" + usersId;

            // when, then
            mvc.perform(delete(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("usersId", usersId.toString()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

            assertFalse(usersRepository.existsUsersByUsersName(usersName));
            assertEquals(0, usersRepository.findAll().size());
        }
    }
}
