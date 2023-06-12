package com.betterme.controller;

import com.betterme.domain.dto.betterme.BetterMeOfTodayResponseDto;
import com.betterme.domain.dto.betterme.BetterMeSaveRequestDto;
import com.betterme.service.BetterMeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @ModelAttribute("habits")
    private Map<String, String> habits() {
        Map<String, String> map = new LinkedHashMap<>();

        map.put("study", "공부 기록");
        map.put("reads", "독서 기록");
        map.put("sleeps", "수면 기록");
        map.put("workouts", "운동 기록");
        map.put("todos", "할일 기록");
        map.put("diets", "식단 기록");
        map.put("waters", "수분 섭취 기록");
        map.put("nutrients", "영영제 섭취 기록");
        map.put("diary", "일기 작성");

        return map;
    }

    @PostMapping("/better-me/new")
    public String save(@Valid BetterMeSaveRequestDto requestDto, BindingResult result) {

        if (result.hasErrors()) {
            return "betterme/createBetterMeForm";
        }

        betterMeService.save(requestDto);

        return "redirect:/better-me";
    }


    @GetMapping("/better-me/today")
    public String getBetterMeOfToday(Model model, Principal principal) {

        if (principal == null) {
            return "users/loginForm";
        }

        BetterMeOfTodayResponseDto responseDto = betterMeService.getBetterMeOfToday(principal.getName(), LocalDate.now());

        model.addAttribute("betterMeOfTodayResponseDto", responseDto);

        return "betterme/betterMeOfToday";
    }

}
