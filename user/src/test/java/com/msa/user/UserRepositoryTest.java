package com.msa.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    //    @Autowired
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @Order(1)
    void addTest() {
        int limit = 3;
        List<User> roleList = IntStream.rangeClosed(1, limit)
                .mapToObj(u ->
                        User.builder().email(u + "@email.com")
                                .name("user" + u)
                                .passwd(passwordEncoder.encode("pwd" + u))
                                .point(0)
                                .build()
                                .addRole(UserRole.ROLE_USER)
                                .addRole(UserRole.ROLE_ADMIN)
                ).toList();

        repository.saveAll(roleList);

//        assertThat(repository.count()).isGreaterThan(limit - 1);
        assertThat(repository.count()).isEqualTo(limit);
    }

    @Test
    @Order(2)
    void readTest() {
        String email = "1@email.com";
        User user = repository.getWithRoles(email);
        System.out.println("user = " + user);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getRoles()).containsAll(List.of(UserRole.ROLE_USER, UserRole.ROLE_ADMIN));
    }

    @Test
    void passwordEncodingTest() {
        String pwd = "pwd00";
        System.out.println("passwordEncoder.encode((pwd)) = " + passwordEncoder.encode((pwd)));
    }
}
