package com.kyj.fmk.repository.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WheatherBgmJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public WheatherBgmJdbcRepository(@Qualifier("wheaterbgmJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
