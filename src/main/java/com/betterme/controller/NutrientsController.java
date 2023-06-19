package com.betterme.controller;

import com.betterme.domain.dto.nutrients.NutrientsResponseDto;
import com.betterme.domain.dto.nutrients.NutrientsSaveRequestDto;
import com.betterme.domain.dto.nutrients.NutrientsUpdateRequestDto;
import com.betterme.service.NutrientsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NutrientsController {

    private final NutrientsService nutrientsService;

    @GetMapping("/nutrients")
    public String getNutrientsIndex(Model model, @RequestParam String betterMeId) {
        List<NutrientsResponseDto> nutrientsList = nutrientsService.getNutrientsList(Long.parseLong(betterMeId));

        model.addAttribute("nutrientsList", nutrientsList);
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("nutrientsSaveRequestDto", new NutrientsSaveRequestDto());

        return "nutrients/nutrientsIndex";
    }

    @PostMapping("/nutrients/new")
    public String save(NutrientsSaveRequestDto requestDto) {
        nutrientsService.save(requestDto);

        return "nutrients/nutrientsIndex";
    }

    @GetMapping("/nutrients/{nutrientsId}")
    public String findById(@PathVariable Long nutrientsId, Model model) {
        NutrientsUpdateRequestDto requestDto = nutrientsService.getUpdateRequestDto(nutrientsId);
        model.addAttribute("nutrientsUpdateRequestDto", requestDto);

        return "nutrients/updateNutrientsForm";
    }

    @PutMapping("/nutrients/{nutrientsId}")
    public String update(@PathVariable Long nutrientsId, NutrientsUpdateRequestDto requestDto, RedirectAttributes redirectAttributes) {
        nutrientsService.update(nutrientsId, requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/nutrients";
    }
}
