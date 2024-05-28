package com.test.batch.infrastructure.writer;

import com.test.batch.application.UserService;
import com.test.batch.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WriterConfig {

    private final UserService userService;

    @StepScope
    @Bean
    public ItemWriter<User> firstWriter() {
        return chunk -> {
            for (User user : chunk.getItems()) {
                try {
                    log.info("도착 유저 아이디 : {}", user.getId());
                    userService.viewUserProfile(user);
                } catch (Exception e) {
                    log.info("예외 발생");
                }
            }
        };
    }
}
