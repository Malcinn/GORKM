package com.gorkm.usersservice.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse {

    private String id;

    private String login;

    private String name;

    private String type;

    private String avatar_url;

    private String created_at;

    private String followers;

    private String public_repos;
}
