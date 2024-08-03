package com.gorkm.usersservice.application;

import java.util.Objects;

public class CalculationServiceImpl implements CalculationService {
    @Override
    public Double calculate(final UserResponse userResponse) {
        if (Objects.nonNull(userResponse) && userResponse.getFollowers() > 0) {
            return 6.0 / userResponse.getFollowers() * (2 + userResponse.getPublic_repos());
        }
        return null;
    }
}
