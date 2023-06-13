package com.betterme.service;

import com.betterme.domain.dto.todos.TodosResponseDto;
import com.betterme.domain.dto.todos.TodosSaveRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Todos;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.TodosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodosService {

    private final TodosRepository todosRepository;

    private final BetterMeRepository betterMeRepository;


    private BetterMe findBetterMeById(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me = " + betterMeId));
    }

    public Long save(TodosSaveRequestDto requestDto) {
        BetterMe betterMe = findBetterMeById(Long.parseLong(requestDto.getBetterMeId()));

        Long savedTodosId = todosRepository.save(requestDto.toEntity(betterMe)).getId();

        log.info("Todos is saved with todos id = " + savedTodosId);

        return savedTodosId;
    }

    public List<TodosResponseDto> getTodosList(Long betterMeId) {
        BetterMe betterMe = findBetterMeById(betterMeId);

        List<TodosResponseDto> todosList = new ArrayList<>();

        for (Todos todos : betterMe.getTodos()) {
            todosList.add(new TodosResponseDto(todos));
        }

        return todosList;
    }
}
