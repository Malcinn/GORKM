package com.gorkm.usersservice.application.event;

import com.gorkm.usersservice.domain.UserApiStats;
import com.gorkm.usersservice.infrastructure.jdbc.UserApiStatsRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import java.math.BigInteger;
import java.util.Objects;

@AllArgsConstructor
public class UserAPICallEventListener implements ApplicationListener<UserAPICallEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAPICallEventListener.class);

    private final UserApiStatsRepository userApiStatsRepository;

    @Override
    public void onApplicationEvent(final UserAPICallEvent event) {
        final String login = event.getLogin();
        if (Objects.nonNull(login)) {
            LOGGER.info("UserAPICallEvent - increasing request count for user: {}", login);
            userApiStatsRepository.findByLogin(login).ifPresentOrElse(
                    userApiStats -> {
                        BigInteger nextRequestCount = new BigInteger(userApiStats.getRequestCount()).add(BigInteger.ONE);
                        userApiStatsRepository.updateByRequestCount(login, nextRequestCount);
                    }, () -> {
                        UserApiStats entity = new UserApiStats(login, null, BigInteger.ONE.toString());
                        userApiStatsRepository.save(entity);
                    });
        }
    }
}
