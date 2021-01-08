package com.spring5.rest.app.services;

import com.spring5.rest.app.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserApiService {
    List<User> getUserData(int limit);

    Flux<User> getUsers(Mono<Integer> limit);
}
