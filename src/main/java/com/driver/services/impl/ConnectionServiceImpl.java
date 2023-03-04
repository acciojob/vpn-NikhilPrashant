package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception {
        User user = userRepository2.findById(userId).get();
        if (user.getConnected() == true) throw new Exception("Already connected");
        if (user.getOriginalCountry().toString().equals(countryName)) {
            System.out.println("Equal");
            return user;
        }
        System.out.println("Not Equal");
        System.out.println(user.getServiceProviderList().size());
        for (ServiceProvider serviceProvider: user.getServiceProviderList()) {
            System.out.println(serviceProvider);
            for (Country country: serviceProvider.getCountryList()) {
                if (country.getCountryName().toString().equals(countryName)) {
                    System.out.println(country.getCountryName().toString());
                    System.out.println(countryName);
                    Connection connection = new Connection(user, serviceProvider);
                    user.setConnected(true);
                    user.setMaskedIp(country.getCode() + "." + serviceProvider.getId() + "." + userId);
                    serviceProvider.getUsers().add(user);
                    serviceProvider.getConnectionList().add(connection);
                    userRepository2.save(user);
                    serviceProviderRepository2.save(serviceProvider);
                    return user;
                }
            }
        }
        throw new Exception("Unable to connect");
    }
    @Override
    public User disconnect(int userId) throws Exception {
        User user = userRepository2.findById(userId).get();
        if (user.getConnected() == false) throw new Exception("Already disconnected");
        user.setConnected(false);
        user.setMaskedIp(null);
        userRepository2.save(user);
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User sender = userRepository2.findById(senderId).get();
        User reciever = userRepository2.findById(receiverId).get();
        String ip = reciever.getMaskedIp() == null? reciever.getOriginalIp(): reciever.getMaskedIp();
        String code = ip.substring(0, 3);
        if (code.equals(sender.getOriginalCountry().getCode())) return sender;
        try {
            sender = connect(senderId, CountryName.valueOf(code).name());
            return sender;
        } catch (Exception e) {
            throw new Exception("Cannot establish communication");
        }
    }
}
