package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class BetterMe extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "betterMe_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    public BetterMe(Users users) {
        this.users = users;
        users.getBetterMes().add(this);
    }

    public float getProgress() {
        // 추후 개발 예정
        return 33.3f;
    }

}
