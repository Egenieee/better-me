package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Todos extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "betterMe_id")
    BetterMe betterMe;

    @Builder
    public Todos(BetterMe betterMe, String content) {
        this.betterMe = betterMe;
        this.content = content;
        this.isCompleted = false;
        betterMe.getTodos().add(this);
    }

    public void update(String content, boolean isCompleted) {
        this.content = content;
        this.isCompleted = isCompleted;
    }
}
