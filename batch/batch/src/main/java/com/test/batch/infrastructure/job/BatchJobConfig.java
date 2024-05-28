package com.test.batch.infrastructure.job;

import com.test.batch.domain.User;
import com.test.batch.exception.UserBatchTestException;
import com.test.batch.infrastructure.skipper.AlwaysSkipItemSkipPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {

    public static final Integer CHUNK_SIZE = 2;

    @Bean
    public Job batchJob(JobRepository jobRepository, Step firstStep) {
        return new JobBuilder("batchJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(firstStep)
                .build();
    }

    @JobScope
    @Bean
    public Step firstStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                          ItemReader firstReader, ItemWriter firstWriter, TaskExecutor taskExecutor) {
        return new StepBuilder("firstStep", jobRepository)
                .<User, User>chunk(CHUNK_SIZE, transactionManager)
                .reader(firstReader)
                .writer(firstWriter)
//                .faultTolerant()
//                .skip(UserBatchTestException.class)
//                .skipLimit(10) // 최대 10개 아이템까지 스킵 허용
                .taskExecutor(taskExecutor)
//                .transactionManager(transactionManager)
                .build();

    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CHUNK_SIZE);
        taskExecutor.setMaxPoolSize(CHUNK_SIZE * 2);
        taskExecutor.setThreadNamePrefix("async-thread");
        return taskExecutor;
    }
}
