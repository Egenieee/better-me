package com.betterme.domain.dto.todos;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Todos;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class TodosSaveRequestDto {

    private String betterMeId;

    @Length(min = 1, message = "할 일을 입력해주세요.")
    private String content;

    public Todos toEntity(BetterMe betterMe) {
        return Todos.builder()
                .betterMe(betterMe)
                .content(content)
                .build();
    }

}
