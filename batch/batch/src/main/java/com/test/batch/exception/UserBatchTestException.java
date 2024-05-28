package com.test.batch.exception;

public class UserBatchTestException extends RuntimeException {

    private static final String MESSAGE = "유저 Batch 테스트 예외 발생";

    public UserBatchTestException() {
        super(MESSAGE);
    }
}
