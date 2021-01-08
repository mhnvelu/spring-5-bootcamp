package com.spring5.rest.app.services;

import com.spring5.rest.app.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserApiServiceImplTest {

    @Autowired
    UserApiService userApiService;

    @Test
    void getUserData() {
        List<User> users = userApiService.getUserData(3);
        assertEquals(1, users.size());
    }
}