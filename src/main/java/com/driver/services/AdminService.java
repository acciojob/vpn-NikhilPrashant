package com.driver.services;

import com.driver.Entities.Admin;
import com.driver.Entities.ServiceProvider;

public interface AdminService {

    public Admin register(String username, String password);
    public Admin addServiceProvider(int adminId, String providerName);
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception;
}