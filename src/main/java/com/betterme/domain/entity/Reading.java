package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Reading extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "better_me_id")
    private BetterMe betterMe;

    @Column
    private String name;

    @Column
    private int firstPage;

    @Column
    private int lastPage;

    @Column(length = 500)
    private String summary;

    @Builder
    public Reading(BetterMe betterMe, String name, int firstPage, int lastPage, String summary) {
        this.betterMe = betterMe;
        this.name = name;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.summary = summary;
    }

    public void update(String name, int firstPage, int lastPage, String summary) {
        this.name = name;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.summary = summary;
    }
}
