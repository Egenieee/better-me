package com.betterme.service;

import com.betterme.domain.dto.workouts.WorkoutsResponseDto;
import com.betterme.domain.dto.workouts.WorkoutsSaveRequestDto;
import com.betterme.domain.dto.workouts.WorkoutsUpdateRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Workouts;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.WorkoutsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutsService {

    private final WorkoutsRepository workoutsRepository;

    private final BetterMeRepository betterMeRepository;

    private Workouts findWorkouts(Long workoutsId) {
        return workoutsRepository.findById(workoutsId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Workouts이 존재하지 않습니다. workouts id = " + workoutsId));
    }

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

    @Transactional
    public Long save(WorkoutsSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMe(requestDto.getBetterMeId());

        Long workoutsId = workoutsRepository.save(requestDto.toEntity(betterMe)).getId();

        log.info("Workouts is saved with workouts id = " + workoutsId);

        return workoutsId;
    }

    public WorkoutsUpdateRequestDto getUpdateRequestDto(Long workoutsId) {
        Workouts workouts = findWorkouts(workoutsId);

        return WorkoutsUpdateRequestDto.builder()
                .workoutsId(workouts.getId())
                .betterMeId(workouts.getBetterMe().getId())
                .name(workouts.getName())
                .details(workouts.getDetails())
                .isCompleted(workouts.isCompleted())
                .build();
    }

    @Transactional
    public void update(Long workoutsId, WorkoutsUpdateRequestDto requestDto) {
        Workouts workouts = findWorkouts(workoutsId);

        workouts.update(requestDto.getName(), requestDto.getDetails(), requestDto.getIsCompleted());

        log.info("Workouts is updated with workouts id = " + workouts.getId());
    }
}
