package com.betterme.controller;

import com.betterme.domain.dto.users.UsersResponseDto;
import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.domain.dto.users.UsersUpdateRequestDto;
import com.betterme.service.UsersService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/users/new")
    public String createForm(Model model) {
        model.addAttribute("usersSaveRequestDto", new UsersSaveRequestDto());
        return "users/createUsersForm";
    }

    @PostMapping("/users/new")
    public String save(@Valid UsersSaveRequestDto usersSaveRequestDto, BindingResult result) {

        if (result.hasErrors()) {
            return "users/createUsersForm";
        }

        usersService.save(usersSaveRequestDto);

        return "redirect:/";
    }

    @GetMapping("/users/login")
    public String login() {
        return "users/loginForm";
    }

    @GetMapping("/users/information")
    public String getInformation(Model model, Principal principal) {

        if (principal == null) {
            return "users/loginForm";
        }

        UsersResponseDto responseDto = usersService.findByUsersName(principal.getName());
        model.addAttribute("usersResponseDto", responseDto);

        return "users/usersInformation";
    }

    @GetMapping("/users/{usersId}")
    public String getUpdateForm(@PathVariable Long usersId, Model model) {
        UsersUpdateRequestDto requestDto = usersService.getUpdateRequestDto(usersId);
        model.addAttribute("usersUpdateRequestDto", requestDto);

        return "users/updateUsersForm";
    }

    @PutMapping("/users/{usersId}")
    public String update(@PathVariable Long usersId, @Valid UsersUpdateRequestDto requestDto, BindingResult result) {

        if (result.hasErrors()) {
            return "users/updateUsersForm";
        }

        usersService.update(usersId, requestDto);

        return "redirect:/";
    }

    @DeleteMapping("/users/{usersId}")
    public String delete(@PathVariable Long usersId, HttpSession session) {
        usersService.delete(usersId);
        session.invalidate(); // 유저 삭제시 세션 삭제

        return "redirect:/";
    }
}
