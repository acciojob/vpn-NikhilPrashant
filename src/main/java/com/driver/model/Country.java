// Note: Do not write @Enumerated annotation above CountryName in this model.

package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "country_name", unique = true)
    private CountryName countryName;
    private String code;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    List<User> userList = new ArrayList<>();

    @ManyToOne
    @JoinColumn
    ServiceProvider serviceProvider;

    public Country() {
    }

    public Country(int id, CountryName countryName, String code, List<User> userList, ServiceProvider serviceProvider) {
        this.id = id;
        this.countryName = countryName;
        this.code = code;
        this.userList = userList;
        this.serviceProvider = serviceProvider;
    }

    public Country(CountryName countryName, ServiceProvider serviceProvider) {
        this.countryName = countryName;
        this.serviceProvider = serviceProvider;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}
