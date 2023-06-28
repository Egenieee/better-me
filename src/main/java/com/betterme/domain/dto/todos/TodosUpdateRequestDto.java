package com.betterme.domain.dto.todos;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class TodosUpdateRequestDto {

    private final Long betterMeId;
    private final Long todosId;

    @Length(min = 1, message = "할 일을 입력하세요.")
    private final String content;

    private final Boolean isCompleted;

    @Builder
    public TodosUpdateRequestDto(Long todosId, Long betterMeId, String content, boolean isCompleted) {
        this.todosId = todosId;
        this.betterMeId = betterMeId;
        this.content = content;
        this.isCompleted = isCompleted;
    }
}
