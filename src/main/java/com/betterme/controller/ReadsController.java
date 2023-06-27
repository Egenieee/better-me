package com.betterme.controller;

import com.betterme.domain.dto.reads.ReadsResponseDto;
import com.betterme.domain.dto.reads.ReadsSaveRequestDto;
import com.betterme.domain.dto.reads.ReadsUpdateRequestDto;
import com.betterme.service.ReadsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/reads/new")
    public String save(@Valid ReadsSaveRequestDto requestDto) {
        readsService.save(requestDto);

        return "reads/readsIndex";
    }

    @GetMapping("/reads/{readsId}")
    public String findById(@PathVariable Long readsId, Model model) {
        ReadsUpdateRequestDto requestDto = readsService.getUpdateRequestDto(readsId);
        model.addAttribute("readsUpdateRequestDto", requestDto);

        return "reads/updateReadsForm";
    }

    @PutMapping("/reads/{readsId}")
    public String update(@PathVariable Long readsId, @Valid ReadsUpdateRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "reads/updateReadsForm";
        }

        readsService.update(readsId, requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/reads";
    }

    @DeleteMapping("/reads/{readsId}")
    public String delete(@PathVariable Long readsId, RedirectAttributes redirectAttributes) {
        Long betterMeId = readsService.getBetterMeId(readsId);
        redirectAttributes.addAttribute("betterMeId", betterMeId);

        readsService.delete(readsId);

        return "redirect:/reads";
    }
}
