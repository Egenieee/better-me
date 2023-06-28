package com.betterme.service;

import com.betterme.domain.dto.workouts.WorkoutsResponseDto;
import com.betterme.domain.dto.workouts.WorkoutsSaveRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Workouts;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.WorkoutsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutsService {

    private final WorkoutsRepository workoutsRepository;

    private final BetterMeRepository betterMeRepository;

    private BetterMe findBetterMe(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me id = " + betterMeId));
    }

    public List<WorkoutsResponseDto> getWorkoutsList(Long betterMeId) {
        BetterMe betterMe = findBetterMe(betterMeId);

        List<WorkoutsResponseDto> workoutsList = new ArrayList<>();

        for (Workouts workouts : betterMe.getWorkouts()) {
            workoutsList.add(new WorkoutsResponseDto(workouts));
        }

        return workoutsList;
    }

    public Long save(WorkoutsSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Long workoutsId = workoutsRepository.save(requestDto.toEntity(betterMe)).getId();

        log.info("Workouts is saved with workouts id = " + workoutsId);

        return workoutsId;
    }
}
