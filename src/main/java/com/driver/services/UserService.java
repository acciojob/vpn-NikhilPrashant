package com.driver.services;

import com.driver.Entities.User;

public interface UserService {

    public User register(String username, String password, String countryName) throws Exception;
    public User subscribe(Integer userId, Integer serviceProviderId);
}