package com.betterme.controller;

import com.betterme.domain.dto.reads.ReadsResponseDto;
import com.betterme.domain.dto.reads.ReadsSaveRequestDto;
import com.betterme.service.ReadsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReadsController {

    private final ReadsService readsService;

    @GetMapping("/reads")
    public String getReadsIndex(Model model, @RequestParam String betterMeId) {
        List<ReadsResponseDto> readsList = readsService.getReadsList(Long.parseLong(betterMeId));

        model.addAttribute("readsList", readsList);
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("readsSaveRequestDto", new ReadsSaveRequestDto());

        return "reads/readsIndex";
    }
}
