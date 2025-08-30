package com.kyj.fmk.config;

import com.kyj.fmk.model.batch.BatchActiveMemDTO;
import com.kyj.fmk.model.batch.BatchExprMemDTO;
import com.kyj.fmk.processor.ActiveMemberProcessor;
import com.kyj.fmk.processor.ExpiredMemberProcessor;
import com.kyj.fmk.reader.ActiveMemberReader;
import com.kyj.fmk.reader.ExpiredMemberReader;
import com.kyj.fmk.writer.ActiveMemberWriter;
import com.kyj.fmk.writer.ExpiredMemberWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobBuilderHelper;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.StepBuilderHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
/**
 * 2025-08-30
 * @author 김용준
 * 배치 잡을 정의
 */
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final ExpiredMemberProcessor processor;
    private final ExpiredMemberWriter writer;

    private final ActiveMemberProcessor activeMemberProcessor;
    private final ActiveMemberWriter activeMemberWriter;

    //---------------------------만료회원 세션종료 배치---------------------------//
    @Bean
    public Job expiredMemberJob(Step expiredMemberStep) {
        return new JobBuilder("expiredMemberJob", jobRepository)
                .start(expiredMemberStep) // Bean으로 주입받음
                .build();
    }

    @Bean
    public Step expiredMemberStep(ExpiredMemberReader expiredMemberReader) {
        return new StepBuilder("expiredMemberStep", jobRepository)
                .<String, BatchExprMemDTO>chunk(100, transactionManager)
                .reader(expiredMemberReader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public ExpiredMemberReader expiredMemberReader(org.springframework.data.redis.core.RedisTemplate<String,Object> redisTemplate) {
        return new ExpiredMemberReader(redisTemplate);
    }
    //---------------------------활성회원 배치---------------------------//
    @Bean
    public Job activeMemberJob(Step activeMemberStep) {
        return new JobBuilder("activeMemberJob", jobRepository)
                .start(activeMemberStep)
                .build();
    }

    @Bean
    public Step activeMemberStep(ActiveMemberReader activeMemberReader) {
        return new StepBuilder("activeMemberStep", jobRepository)
                .<String, BatchActiveMemDTO>chunk(100, transactionManager)
                .reader(activeMemberReader)
                .processor(activeMemberProcessor)
                .writer(activeMemberWriter)
                .build();
    }

    @Bean
    @StepScope
    public ActiveMemberReader activeMemberReader(org.springframework.data.redis.core.RedisTemplate<String,Object> redisTemplate,
                                                 com.fasterxml.jackson.databind.ObjectMapper objectMapper) {
        return new ActiveMemberReader(redisTemplate, objectMapper);
    }
}


