package com.kyj.fmk.config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class DataSourceConfig {

    // ---------------- Batch DB ----------------
    @Bean(name = "batchDataSource")
    @BatchDataSource
    @ConfigurationProperties(prefix = "spring.datasource.batch")
    @Primary
    public DataSource batchDataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create().build();
    }

    // ---------------- Member DB ----------------
    @Bean(name = "memberDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.member")
    public DataSource memberDataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create().build();
    }

    @Bean(name = "memberJdbcTemplate")
    public JdbcTemplate memberJdbcTemplate(@Qualifier("memberDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "memberSqlSessionFactory")
    public SqlSessionFactory memberSqlSessionFactory(@Qualifier("memberDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/member/**/*.xml")
        );
        return factoryBean.getObject();
    }

    @Bean(name = "memberSqlSessionTemplate")
    public SqlSessionTemplate memberSqlSessionTemplate(@Qualifier("memberSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // ---------------- Bottle DB ----------------
    @Bean(name = "bottleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.bottle")
    public DataSource bottleDataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create().build();
    }

    @Bean(name = "bottleJdbcTemplate")
    public JdbcTemplate bottleJdbcTemplate(@Qualifier("bottleDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "bottleSqlSessionFactory")
    public SqlSessionFactory bottleSqlSessionFactory(@Qualifier("bottleDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/bottle/**/*.xml")
        );
        return factoryBean.getObject();
    }

    @Bean(name = "bottleSqlSessionTemplate")
    public SqlSessionTemplate bottleSqlSessionTemplate(@Qualifier("bottleSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // ---------------- WheaterBGM DB ----------------
    @Bean(name = "wheaterbgmDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wheaterbgm")
    public DataSource wheaterbgmDataSource() {
        return org.springframework.boot.jdbc.DataSourceBuilder.create().build();
    }

    @Bean(name = "wheaterbgmJdbcTemplate")
    public JdbcTemplate wheaterbgmJdbcTemplate(@Qualifier("wheaterbgmDataSource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "wheaterbgmSqlSessionFactory")
    public SqlSessionFactory wheaterbgmSqlSessionFactory(@Qualifier("wheaterbgmDataSource") DataSource ds) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/mappers/wheaterbgm/**/*.xml")
        );
        return factoryBean.getObject();
    }

    @Bean(name = "wheaterbgmSqlSessionTemplate")
    public SqlSessionTemplate wheaterbgmSqlSessionTemplate(@Qualifier("wheaterbgmSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // ---------------- 트랜잭션 매니저 ----------------
    @Bean
    public DataSourceTransactionManager memberTxManager(@Qualifier("memberDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public DataSourceTransactionManager bottleTxManager(@Qualifier("bottleDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public DataSourceTransactionManager wheaterbgmTxManager(@Qualifier("wheaterbgmDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }


}
