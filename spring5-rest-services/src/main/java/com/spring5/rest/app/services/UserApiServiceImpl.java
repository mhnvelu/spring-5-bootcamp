package com.spring5.rest.app.services;

import com.spring5.rest.app.domain.User;
import com.spring5.rest.app.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserApiServiceImpl implements UserApiService {

    private RestTemplate restTemplate;

    private String apiUrl;

    public UserApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public List<User> getUserData(int limit) {

        UriComponentsBuilder uriComponentsBuilder =
                UriComponentsBuilder.fromUriString(apiUrl).queryParam("limit", limit);

        UserData userData =
                restTemplate.getForObject(uriComponentsBuilder.toUriString(), UserData.class);
        return userData.getData();
    }

    @Override
    public Flux<User> getUsers(Mono<Integer> limit) {
        return WebClient.create(apiUrl).get()
                .uri(uriBuilder -> uriBuilder.queryParam("limit", limit.block()).build())
                .accept(MediaType.APPLICATION_JSON).exchange()
                .flatMap(response -> response.bodyToMono(UserData.class))
                .flatMapIterable(UserData::getData);
    }
}
