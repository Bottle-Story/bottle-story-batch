package com.kyj.fmk.repository.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BottleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public BottleJdbcRepository(@Qualifier("bottleJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
