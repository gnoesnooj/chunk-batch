package com.test.batch.infrastructure.reader;

import com.test.batch.domain.User;
import com.test.batch.infrastructure.job.BatchJobConfig;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReaderConfig {

    private final EntityManagerFactory entityManagerFactory;

    @StepScope
    @Bean
    public JpaPagingItemReader<User> firstReader(){
        String query = "select u from User u";
        return new JpaPagingItemReaderBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .queryString(query)
                .pageSize(BatchJobConfig.CHUNK_SIZE)
                .name("firstReader")
                .build();
    }
}
