package com.gorkm.usersservice.infrastructure.jdbc;

import com.gorkm.usersservice.domain.UserApiStats;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

public interface UserApiStatsRepository extends Repository<UserApiStats, Long> {

    Optional<UserApiStats> findByLogin(String login);

    UserApiStats save(UserApiStats entity);

    @Modifying
    @Query("UPDATE user_api_stats SET request_count=:requestCount WHERE login=:login")
    boolean updateByRequestCount(@Param("login") String login, @Param("requestCount") BigInteger requestCount);
}
