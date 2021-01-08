package com.spring5.rest.app.services;

import com.spring5.rest.app.domain.User;
import com.spring5.rest.app.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
}
