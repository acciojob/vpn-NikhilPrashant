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
        System.out.println("Here");
        User user = userRepository2.findById(userId).get();
        if (user.getMaskedIp() != null) throw new Exception("Already connected");
        if (user.getOriginalCountry().toString().equals(countryName)) {
            return user;
        }
        for (ServiceProvider serviceProvider: user.getServiceProviderList()) {
            for (Country country: serviceProvider.getCountryList()) {
                if (country.getCountryName().toString().equals(countryName)) {
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
        if (user.getMaskedIp() == null) throw new Exception("Already disconnected");
        user.setConnected(false);
        user.setMaskedIp(null);
        userRepository2.save(user);
        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {
        User sender = userRepository2.findById(senderId).get();
        User reciever = userRepository2.findById(receiverId).get();
        String recieverIp = reciever.getMaskedIp() == null? reciever.getOriginalIp(): reciever.getMaskedIp();
        String recieverCode = recieverIp.substring(0, 3);
        String senderIp = sender.getMaskedIp() == null? sender.getOriginalIp(): sender.getMaskedIp();
        String senderCode = senderIp.substring(0, 3);
        if (senderCode.equals(recieverCode)) return sender;
        try {
            sender.setMaskedIp(null);
            userRepository2.save(sender);
            CountryName[] countryNames = CountryName.values();
            for (CountryName countryName: countryNames) {
                if (countryName.toCode().toString().equals(recieverCode)) {
                    sender = connect(senderId, countryName.toString());
                    return sender;
                }
            }
            throw new Exception();
        } catch (Exception e) {
            throw new Exception("Cannot establish communication");
        }
    }
}
