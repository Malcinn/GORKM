package com.gorkm.usersservice.application;

public class CalculationServiceImpl implements CalculationService {
    @Override
    public Double calculate(UserResponse userResponse) {
        return 6.0 / userResponse.getFollowers() * (2 + userResponse.getPublic_repos());
    }
}
