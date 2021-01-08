package com.spring5.rest.app.services;

import com.spring5.rest.app.domain.User;
import com.spring5.rest.app.domain.UserData;

import java.util.List;

public interface UserApiService {
    List<User> getUserData(int limit);
}
