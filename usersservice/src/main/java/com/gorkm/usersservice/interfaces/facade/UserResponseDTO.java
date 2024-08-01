package com.gorkm.usersservice.interfaces.facade;

public record UserResponseDTO(String id,
                              String login,
                              String name,
                              String avatarUrl,
                              String createdAt,
                              Double calculations) {

}
