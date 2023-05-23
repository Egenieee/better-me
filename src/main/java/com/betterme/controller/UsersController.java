package com.betterme.controller;

import com.betterme.domain.dto.users.UsersSaveRequestDto;
import com.betterme.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
}
