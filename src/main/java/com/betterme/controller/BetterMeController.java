package com.betterme.controller;

import com.betterme.domain.dto.betterme.BetterMeSaveRequestDto;
import com.betterme.service.BetterMeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BetterMeController {

    private final BetterMeService betterMeService;

    @GetMapping("/better-me")
    public String getBetterMeIndex(Model model, Principal principal) {

        if (principal == null) {
            return "users/loginForm";
        }

        boolean hasBetterMeOfToday = betterMeService.hasBetterMeOfToday(principal.getName(), LocalDate.now());

        model.addAttribute("hasBetterMeOfToday", hasBetterMeOfToday);

        return "betterme/betterMeIndex";
    }

    @GetMapping("/better-me/new")
    public String createBetterMeForm(Model model, Principal principal) {

        if (principal == null) {
            return "users/loginForm";
        }

        BetterMeSaveRequestDto requestDto = betterMeService.getBetterMeSaveRequestDto(principal.getName(), LocalDate.now());

        model.addAttribute("betterMeSaveRequestDto", requestDto);

        return "betterme/createBetterMeForm";
    }

    @PostMapping("/better-me/new")
    public String save(BetterMeSaveRequestDto requestDto, BindingResult result) {
        betterMeService.save(requestDto);

        return "redirect:/better-me";
    }


    @GetMapping("/better-me/today")
    public String getBetterMeOfToday(Model model, Principal principal) {

        if (principal == null) {
            return "users/loginForm";
        }

        int progress = betterMeService.getProgressOfToday(principal.getName(), LocalDate.now());

        model.addAttribute("progress", progress);

        return "betterme/betterMeOfToday";
    }

}
