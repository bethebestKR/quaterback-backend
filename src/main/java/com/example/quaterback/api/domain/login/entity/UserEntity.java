package com.example.quaterback.api.domain.login.entity;

import com.example.quaterback.api.domain.login.domain.UserDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    protected UserEntity() {
    }

    @Builder
    private UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserEntity from(UserDomain userDomain) {
        return UserEntity.builder()
                .username(userDomain.getUsername())
                .password(userDomain.getPassword())
                .build();
    }

    public UserDomain toDomain() {
        return UserDomain.builder()
                .username(username)
                .build();
    }

}
