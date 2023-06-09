package com.betterme.controller;

import com.betterme.domain.dto.sleeps.SleepsResponseDto;
import com.betterme.domain.dto.sleeps.SleepsSaveRequestDto;
import com.betterme.domain.dto.sleeps.SleepsUpdateRequestDto;
import com.betterme.service.SleepsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String save(@Valid SleepsSaveRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("betterMeId", requestDto.getBetterMeId());
            return "sleeps/createSleepsForm";
        }

        sleepsService.save(requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/sleeps";
    }


    @GetMapping("/sleeps/{sleepsId}/edit")
    public String createUpdateSleepsForm(@PathVariable Long sleepsId, Model model) {
        SleepsUpdateRequestDto requestDto = sleepsService.getSleepsUpdateRequestDto(sleepsId);

        model.addAttribute("sleepsUpdateRequestDto", requestDto);

        return "sleeps/updateSleepsForm";
    }

    @PutMapping("/sleeps/{sleepsId}")
    public String update(@PathVariable Long sleepsId, @Valid SleepsUpdateRequestDto requestDto, BindingResult result) {

        if (result.hasErrors()) {
            return "sleeps/updateSleepsForm";
        }

        sleepsService.update(requestDto);

        return "redirect:/sleeps/" + sleepsId;
    }

    @DeleteMapping("/sleeps/{sleepsId}")
    public String delete(@PathVariable Long sleepsId, RedirectAttributes redirectAttributes) {
        Long betterMeId = sleepsService.getBetterMeId(sleepsId);
        redirectAttributes.addAttribute("betterMeId", betterMeId);

        sleepsService.delete(sleepsId);

        return "redirect:/sleeps";
    }
}
