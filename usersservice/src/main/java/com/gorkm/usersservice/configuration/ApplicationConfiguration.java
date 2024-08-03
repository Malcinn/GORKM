package com.gorkm.usersservice.configuration;

import com.gorkm.usersservice.application.CalculationService;
import com.gorkm.usersservice.application.CalculationServiceImpl;
import com.gorkm.usersservice.application.UserService;
import com.gorkm.usersservice.application.UserServiceImpl;
import com.gorkm.usersservice.application.event.UserAPICallEventListener;
import com.gorkm.usersservice.infrastructure.jdbc.UserApiStatsRepository;
import com.gorkm.usersservice.interfaces.facade.UserFacade;
import com.gorkm.usersservice.interfaces.facade.UserFacadeImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableCaching
public class ApplicationConfiguration {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public CalculationService calculationService() {
        return new CalculationServiceImpl();
    }

    @Bean
    public UserService userService(WebClient webClient) {
        return new UserServiceImpl(webClient);
    }

    @Bean
    public UserFacade userFacade(UserService userService, CalculationService calculationService) {
        return new UserFacadeImpl(userService, calculationService);
    }

    @Bean
    public UserAPICallEventListener userAPICallEventListener(UserApiStatsRepository userApiStatsRepository) {
        return new UserAPICallEventListener(userApiStatsRepository);
    }

}
