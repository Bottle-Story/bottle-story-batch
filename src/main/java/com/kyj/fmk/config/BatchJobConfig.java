package com.kyj.fmk.config;

import com.kyj.fmk.model.batch.BatchExprMemDTO;
import com.kyj.fmk.processor.ExpiredMemberProcessor;
import com.kyj.fmk.reader.ExpiredMemberReader;
import com.kyj.fmk.writer.ExpiredMemberWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
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
    private final ExpiredMemberReader reader;
    private final ExpiredMemberProcessor processor;
    private final ExpiredMemberWriter writer;

    //---------------------------만료회원 세션종료 배치---------------------------//
    @Bean
    public Job expiredMemberJob() {
        return new JobBuilder("expiredMemberJob", jobRepository)
                .start(expiredMemberStep())
                .build();
    }

    @Bean
    public Step expiredMemberStep() {
        return new StepBuilder("expiredMemberStep", jobRepository)
                .<String, BatchExprMemDTO>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
