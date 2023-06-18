package com.betterme.controller;

import com.betterme.service.SleepsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SleepsController {

    private final SleepsService sleepsService;

    @GetMapping("/sleeps")
    public String getSleepsIndex(Model model, @RequestParam String betterMeId) {
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("hasSleeps", sleepsService.hasSleeps(Long.parseLong(betterMeId)));

        return "sleeps/sleepsIndex";
    }
}
