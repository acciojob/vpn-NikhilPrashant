package com.driver.services.impl;

import Enums.CountryName;
import com.driver.Entities.Country;
import com.driver.Entities.ServiceProvider;
import com.driver.Entities.User;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository3;
    @Autowired
    ServiceProviderRepository serviceProviderRepository3;
    @Autowired
    CountryRepository countryRepository3;

    @Override
    public User register(String username, String password, String countryName) throws Exception{
        if (CountryName.valueOf(countryName).toString() != countryName) throw new Exception("Country not found");
        List<Country> countryList = countryRepository3.findAll();
        for (Country country: countryList) {
            if (country.getCountryName().toString().equals(countryName)) {
                User user = new User(username, password, "countryCode.userId", country);
                userRepository3.save(user);
                return user;
            }
        }
        return new User();
    }

    @Override
    public User subscribe(Integer userId, Integer serviceProviderId) {
        User user = userRepository3.findById(userId).get();
        ServiceProvider serviceProvider = serviceProviderRepository3.findById(serviceProviderId).get();
        user.getServiceProviderList().add(serviceProvider);
        serviceProvider.getUserList().add(user);
        return user;
    }
}
