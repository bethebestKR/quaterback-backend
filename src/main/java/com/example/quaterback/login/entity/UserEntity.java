package com.example.quaterback.login.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    private String username;
    @Setter
    private String password;

    protected UserEntity() {
    }

    private UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserEntity of(String username, String password) {
        return new UserEntity(username, password);
    }

    public static UserEntity of(String username) {
        return new UserEntity(username, null);
    }

}
