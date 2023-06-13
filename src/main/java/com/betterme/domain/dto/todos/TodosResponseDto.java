package com.betterme.domain.dto.todos;

import com.betterme.domain.entity.Todos;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TodosResponseDto {

    private final Long todosId;
    private final String content;
    private final boolean isComplete;

    @Builder
    public TodosResponseDto(Todos todos) {
        this.todosId = todos.getId();
        this.content = todos.getContent();
        this.isComplete = todos.isCompleted();
    }
}
