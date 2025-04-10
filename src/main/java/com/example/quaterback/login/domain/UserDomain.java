package com.example.quaterback.login.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDomain {
    String username;
    String password;

    public static UserDomain createUserDomain(String username, String password) {
        return UserDomain.builder()
                .username(username)
                .password(password)
                .build();
    }
}
