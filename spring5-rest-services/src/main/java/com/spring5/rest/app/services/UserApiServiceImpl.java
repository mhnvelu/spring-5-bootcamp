package com.spring5.rest.app.services;

import com.spring5.rest.app.domain.User;
import com.spring5.rest.app.domain.UserData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserApiServiceImpl implements UserApiService {

    RestTemplate restTemplate;

    public UserApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUserData(int limit) {
        UserData userData = restTemplate.getForObject(
                "https://private-anon-75abfa99b2" + "-apifaketory" + ".apiary-mock" +
                ".com/api/user", UserData.class);
        return userData.getData();
    }
}
