package com.betterme.controller;

import com.betterme.domain.dto.workouts.WorkoutsResponseDto;
import com.betterme.domain.dto.workouts.WorkoutsSaveRequestDto;
import com.betterme.service.WorkoutsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
