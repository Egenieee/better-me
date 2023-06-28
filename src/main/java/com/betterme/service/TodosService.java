package com.betterme.service;

import com.betterme.domain.dto.todos.TodosResponseDto;
import com.betterme.domain.dto.todos.TodosSaveRequestDto;
import com.betterme.domain.dto.todos.TodosUpdateRequestDto;
import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Todos;
import com.betterme.repository.BetterMeRepository;
import com.betterme.repository.TodosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TodosService {

    private final TodosRepository todosRepository;

    private final BetterMeRepository betterMeRepository;

    private Todos findTodos(Long todosId) {
        return todosRepository.findById(todosId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Todos가 존재하지 않습니다. todos id = " + todosId));
    }

    private BetterMe findBetterMeById(Long betterMeId) {
        return betterMeRepository.findById(betterMeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Better Me가 존재하지 않습니다. better me = " + betterMeId));
    }

    public Long getBetterMeId(Long todosId) {
        Todos todos = findTodos(todosId);

        return todos.getBetterMe().getId();
    }

    @Transactional
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

    public TodosUpdateRequestDto getUpdateRequestDto(Long todosId) {
        Todos todos = findTodos(todosId);

        return TodosUpdateRequestDto.builder()
                .todosId(todos.getId())
                .content(todos.getContent())
                .isCompleted(todos.isCompleted())
                .betterMeId(todos.getBetterMe().getId())
                .build();
    }

    @Transactional
    public void update(Long todosId, TodosUpdateRequestDto requestDto) {
        Todos todos = findTodos(todosId);
        todos.update(requestDto.getContent(), requestDto.getIsCompleted());

        log.info("Todos is update withed todos id = " + todosId);
    }

    @Transactional
    public void delete(Long todosId) {
        Todos todos = findTodos(todosId);
        todosRepository.delete(todos);

        log.info("Todos is delete with todos id = " + todosId);
    }
}
