package com.gorkm.usersservice.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Getter
@Table(name = "user_api_stats")
public class UserApiStats {

    @Id
    private final String login;

    @Version
    private final Integer version;

    /**
     * keeping request count as String(VARCHAR on db side) can cause problem with conversion in case someone corrupt data on DB
     * ,but it gives us ability to hold extremly big number as a string and then convert it into BigInteger on Java side.
     */
    private final String requestCount;
}
