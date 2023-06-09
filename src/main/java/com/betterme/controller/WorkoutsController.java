package com.betterme.controller;

import com.betterme.domain.dto.workouts.WorkoutsResponseDto;
import com.betterme.domain.dto.workouts.WorkoutsSaveRequestDto;
import com.betterme.domain.dto.workouts.WorkoutsUpdateRequestDto;
import com.betterme.service.WorkoutsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class WorkoutsController {

    private final WorkoutsService workoutsService;

    @GetMapping("/workouts")
    public String getReadsIndex(Model model, @RequestParam String betterMeId) {
        List<WorkoutsResponseDto> workoutsList = workoutsService.getWorkoutsList(Long.parseLong(betterMeId));

        model.addAttribute("workoutsList", workoutsList);
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("workoutsSaveRequestDto", new WorkoutsSaveRequestDto());

        return "workouts/workoutsIndex";
    }

    @PostMapping("/workouts/new")
    public String save(WorkoutsSaveRequestDto requestDto) {
        workoutsService.save(requestDto);

        return "workouts/workoutsIndex";
    }

    @GetMapping("/workouts/{workoutsId}")
    public String findById(@PathVariable Long workoutsId, Model model) {
        WorkoutsUpdateRequestDto requestDto = workoutsService.getUpdateRequestDto(workoutsId);
        model.addAttribute("workoutsUpdateRequestDto", requestDto);

        return "workouts/updateWorkoutsForm";
    }

    @PutMapping("/workouts/{workoutsId}")
    public String update(@PathVariable Long workoutsId, WorkoutsUpdateRequestDto requestDto, RedirectAttributes redirectAttributes) {
        workoutsService.update(workoutsId, requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/workouts";
    }

    @DeleteMapping("/workouts/{workoutsId}")
    public String delete(@PathVariable Long workoutsId, RedirectAttributes redirectAttributes) {
        Long betterMeId = workoutsService.getBetterMeId(workoutsId);
        workoutsService.delete(workoutsId);

        redirectAttributes.addAttribute("betterMeId", betterMeId);

        return "redirect:/workouts";
    }

}
