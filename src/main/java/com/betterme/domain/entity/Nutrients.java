package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Nutrients extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean isTaken;

    @ManyToOne
    @JoinColumn(name = "nutrients_id")
    private BetterMe betterMe;

    @Builder
    public Nutrients(BetterMe betterMe, String name) {
        this.betterMe = betterMe;
        this.name = name;
        this.isTaken = false;
        betterMe.getNutrients().add(this);
    }
}
