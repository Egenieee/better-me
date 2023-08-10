package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
@Entity
public class BetterMe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @ElementCollection
    private Map<String, String> habitsAndUrl = new LinkedHashMap<>();

    @OneToMany(mappedBy = "betterMe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todos> todos = new ArrayList<>();

    @OneToMany(mappedBy = "betterMe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nutrients> nutrients = new ArrayList<>();

    @OneToMany(mappedBy = "betterMe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reading> reading = new ArrayList<>();

    @OneToMany(mappedBy = "betterMe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Workouts> workouts = new ArrayList<>();

    @OneToOne(mappedBy = "betterMe", orphanRemoval = true)
    private Sleeps sleeps;

    @Builder
    public BetterMe(Users users) {
        this.users = users;
        users.getBetterMes().add(this);
    }

    public float getProgress() {
        // 추후 개발 예정
        return 33.3f;
    }

    public void joinSleeps(Sleeps sleeps) {
        this.sleeps = sleeps;
    }

    public void setHabitsAndUrl(List<String> habits) {
//        if (habits.contains("study")) {
//            this.habitsAndUrl.put("공부 기록 관리", "/study");
//        }

        if (habits.contains("reading")) {
            this.habitsAndUrl.put("독서 기록 관리", "/reading");
        }

        if (habits.contains("sleeps")) {
            this.habitsAndUrl.put("수면 기록 관리", "/sleeps");
        }

        if (habits.contains("workouts")) {
            this.habitsAndUrl.put("운동 기록 관리", "/workouts");
        }

        if (habits.contains("todos")) {
            this.habitsAndUrl.put("할일 기록 관리", "/todos");
        }

//        if (habits.contains("diets")) {
//            this.habitsAndUrl.put("식단 기록 관리", "/diets");
//        }
//
//        if (habits.contains("waters")) {
//            this.habitsAndUrl.put("수분 섭취 관리", "/waters");
//        }

        if (habits.contains("nutrients")) {
            this.habitsAndUrl.put("영양제 섭취 관리", "/nutrients");
        }

//        if (habits.contains("diary")) {
//            this.habitsAndUrl.put("일기 관리", "/diary");
//        }
    }
}