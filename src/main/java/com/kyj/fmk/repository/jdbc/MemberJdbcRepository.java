package com.kyj.fmk.repository.jdbc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberJdbcRepository(@Qualifier("memberJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
