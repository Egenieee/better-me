package com.betterme.controller;

import com.betterme.domain.dto.sleeps.SleepsResponseDto;
import com.betterme.domain.dto.sleeps.SleepsSaveRequestDto;
import com.betterme.service.SleepsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class SleepsController {

    private final SleepsService sleepsService;

    @GetMapping("/sleeps")
    public String getSleepsIndex(Model model, @RequestParam String betterMeId) {
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("hasSleeps", sleepsService.hasSleeps(betterMeId));

        if (sleepsService.hasSleeps(betterMeId)) {
            model.addAttribute("sleepsId", sleepsService.getSleepsId(betterMeId));
        }

        return "sleeps/sleepsIndex";
    }

    @GetMapping("/sleeps/{sleepsId}")
    public String findById(Model model, @PathVariable Long sleepsId) {
        SleepsResponseDto responseDto = sleepsService.getSleepsResponseDto(sleepsId);

        model.addAttribute("sleepsResponseDto", responseDto);

        return "sleeps/sleeps";
    }

    @GetMapping("/sleeps/new")
    public String createSleepsForm(Model model, @RequestParam String betterMeId) {
        model.addAttribute("sleepsSaveRequestDto", new SleepsSaveRequestDto());
        model.addAttribute("betterMeId", betterMeId);

        return "sleeps/createSleepsForm";
    }

    @PostMapping("/sleeps/new")
    public String save(@Valid SleepsSaveRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "sleeps/createSleepsForm";
        }

        sleepsService.save(requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/sleeps";
    }
}
