package com.test.batch.infrastructure.skipper;

import com.test.batch.exception.UserBatchTestException;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

@NoArgsConstructor
public class AlwaysSkipItemSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        return true;
    }
}
