package com.betterme.controller;

import com.betterme.domain.dto.users.UsersResponseDto;
import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.domain.dto.users.UsersUpdateRequestDto;
import com.betterme.service.UsersService;
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
        UsersResponseDto responseDto = usersService.findByUserName(principal.getName());
        model.addAttribute("usersResponseDto", responseDto);

        return "users/userInformation";
    }

    @GetMapping("/users/{userId}")
    public String getUpdateForm(@PathVariable Long userId, Model model) {
        UsersUpdateRequestDto requestDto = usersService.getUpdateRequestDto(userId);
        model.addAttribute("usersUpdateRequestDto", requestDto);

        return "users/updateUsersForm";
    }

    @PutMapping("/users/{userId}")
    public String update(@PathVariable Long userId, @Valid @ModelAttribute("usersUpdateRequestDto") UsersUpdateRequestDto requestDto) {
        usersService.update(userId, requestDto);

        return "redirect:/";
    }
}
