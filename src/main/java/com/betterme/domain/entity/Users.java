package com.betterme.domain.entity;

import com.betterme.domain.dto.betterme.BetterMeOfPastResponseDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String usersName;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String slogan;

    @OneToMany(mappedBy = "users")
    List<BetterMe> betterMes = new ArrayList<>();

    @Builder
    public Users(String name, String nickname, String email, String password, String slogan) {
        this.usersName = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.slogan = slogan;
    }

    public void update(String nickname, String email, String slogan) {
        this.nickname = nickname;
        this.email = email;
        this.slogan = slogan;
    }

    public boolean hasBetterMeOfToday(LocalDate today) {
        for (BetterMe betterMe : betterMes) {
            if (betterMe.getCreatedDate().toLocalDate().isEqual(today)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasBetterMeOfPast(LocalDate today) {
        for (BetterMe betterMe : betterMes) {
            if (betterMe.getCreatedDate().toLocalDate().isBefore(today)) {
                return true;
            }
        }

        return false;
    }

    public BetterMe getBetterMeOfToday(LocalDate today) {
        for (BetterMe betterMe : betterMes) {
            if (betterMe.getCreatedDate().toLocalDate().isEqual(today)) {
                return betterMe;
            }
        }

        return null;
    }

    public List<BetterMeOfPastResponseDto> getBetterMeOfPastList() {
        LocalDate today = LocalDate.now();
        List<BetterMeOfPastResponseDto> responseDtoList = new ArrayList<>();

        for (BetterMe betterMe : betterMes) {
            if (betterMe.getCreatedDate().toLocalDate().isBefore(today)) {
                responseDtoList.add(new BetterMeOfPastResponseDto(betterMe));
            }
        }

        return responseDtoList;
    }
}
