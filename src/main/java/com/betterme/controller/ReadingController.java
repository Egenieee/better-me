package com.betterme.controller;

import com.betterme.domain.dto.reads.ReadingResponseDto;
import com.betterme.domain.dto.reads.ReadingSaveRequestDto;
import com.betterme.domain.dto.reads.ReadingUpdateRequestDto;
import com.betterme.service.ReadingService;
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
public class ReadingController {

    private final ReadingService readingService;

    @GetMapping("/reading")
    public String getReadsIndex(Model model, @RequestParam String betterMeId) {
        List<ReadingResponseDto> readingList = readingService.getReadsList(Long.parseLong(betterMeId));

        model.addAttribute("readingList", readingList);
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("readingSaveRequestDto", new ReadingSaveRequestDto());

        return "reading/readingIndex";
    }

    @PostMapping("/reading/new")
    public String save(@Valid ReadingSaveRequestDto requestDto) {
        readingService.save(requestDto);

        return "reading/readingIndex";
    }

    @GetMapping("/reading/{readingId}")
    public String findById(@PathVariable Long readingId, Model model) {
        ReadingUpdateRequestDto requestDto = readingService.getUpdateRequestDto(readingId);
        model.addAttribute("readingUpdateRequestDto", requestDto);

        return "reading/updateReadingForm";
    }

    @PutMapping("/reads/{readingId}")
    public String update(@PathVariable Long readingId, @Valid ReadingUpdateRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "reading/updateReadingForm";
        }

        readingService.update(readingId, requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/reading";
    }

    @DeleteMapping("/reads/{readingId}")
    public String delete(@PathVariable Long readingId, RedirectAttributes redirectAttributes) {
        Long betterMeId = readingService.getBetterMeId(readingId);
        redirectAttributes.addAttribute("betterMeId", betterMeId);

        readingService.delete(readingId);

        return "redirect:/reading";
    }
}
