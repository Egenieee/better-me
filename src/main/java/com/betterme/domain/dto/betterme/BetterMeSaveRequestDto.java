package com.betterme.domain.dto.betterme;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Users;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class BetterMeSaveRequestDto {

    private final String usersName;
    private final String today;

    @Size(min = 1, message = "관리할 습관을 한 가지 이상 선택해야 합니다.")
    private List<String> habits;

    @Builder
    public BetterMeSaveRequestDto(String usersName) {
        this.usersName = usersName;
        this.today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
    }

    public BetterMe toEntity(Users users) {
        return BetterMe.builder()
                .users(users)
                .build();
    }
}
