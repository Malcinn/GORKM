package com.gorkm.usersservice.configuration;

import com.gorkm.usersservice.application.UserService;
import com.gorkm.usersservice.application.UserServiceImpl;
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
    public UserService userService(WebClient webClient) {
        return new UserServiceImpl(webClient);
    }

    @Bean
    UserFacade userFacade(UserService userService) {
        return new UserFacadeImpl(userService);
    }

}
