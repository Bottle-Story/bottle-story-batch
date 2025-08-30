package com.kyj.fmk.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * 2025-08-30
 * @author 김용준
 * 배치 잡수행을 하는 스케쥴러
 */
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job expiredMemberJob;

    /**
     * 만료된 회원을 조회하여 카프카로 이벤트 전송 (5분마다)
     * @throws Exception
     */
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void runJob() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(expiredMemberJob,
                jobParameters);
    }
}
