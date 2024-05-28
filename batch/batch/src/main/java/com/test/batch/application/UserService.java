package com.test.batch.application;

import com.test.batch.domain.User;
import com.test.batch.domain.repository.UserRepository;
import com.test.batch.exception.UserBatchTestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    @Transactional
    public void viewUserProfile(User user) {
        isNumberHundred(user.getId());
        user.viewProfile();
        userRepository.save(user);
    }

    private void isNumberHundred(long id) {
        if (id == 5) {
            throw new UserBatchTestException();
        }
    }

    public void inputDatas() {
        List<User> users = new ArrayList<>();

        for (int i = 1; i < 101; i++) {
            users.add(User.of("유저" + i));
        }

        userRepository.saveAll(users);
    }
}
