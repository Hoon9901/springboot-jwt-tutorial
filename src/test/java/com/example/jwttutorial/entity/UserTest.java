package com.example.jwttutorial.entity;

import com.example.jwttutorial.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.yml")
class UserTest {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @WithMockUser(username = "test", roles = "USER")
    public void 이용자_저장_테스트 () {
        User user = new User();
        userRepository.save(user);

        em.flush();
        em.clear();

        User savedUser = userRepository.findById(user.getUserId())
                .orElseThrow(EntityNotFoundException::new);

        assertThat(user.getUserId()).isEqualTo(savedUser.getUserId());
    }

}