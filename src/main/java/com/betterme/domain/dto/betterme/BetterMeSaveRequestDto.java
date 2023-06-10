package com.betterme.domain.dto.betterme;

import com.betterme.domain.entity.BetterMe;
import com.betterme.domain.entity.Users;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class BetterMeSaveRequestDto {

    private final String usersName;
    private final String today;

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
