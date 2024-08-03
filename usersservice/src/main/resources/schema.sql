CREATE TABLE IF NOT EXISTS user_api_stats (
    login VARCHAR(255) not null,
    version int,
    request_count VARCHAR(255) not null,
    PRIMARY KEY (login)
);
