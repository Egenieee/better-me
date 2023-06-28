package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Workouts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workouts")
    private BetterMe betterMe;

    @Column
    private String name;

    @Column
    private String details;

    @Column
    private boolean isCompleted;

    @Builder
    public Workouts(BetterMe betterMe, String name, String details) {
        this.betterMe = betterMe;
        this.name = name;
        this.details = details;
        this.isCompleted = false;
    }

    public void update(String name, String details, boolean isCompleted) {
        this.name = name;
        this.details = details;
        this.isCompleted = isCompleted;
    }
}
