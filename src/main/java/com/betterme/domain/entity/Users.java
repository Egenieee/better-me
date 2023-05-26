package com.betterme.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private String slogan;

    @Builder
    public Users(String name, String email, String password, String slogan) {
        this.userName = name;
        this.email = email;
        this.password = password;
        this.slogan = slogan;
    }

}
