package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "service_providers")
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn
    Admin admin;

    @ManyToMany
    List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    List<Connection> connectionList = new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider", cascade = CascadeType.ALL)
    List<Country> countryList = new ArrayList<>();

    public ServiceProvider() {
    }

    public ServiceProvider(int id, String name, Admin admin, List<User> userList, List<Connection> connectionList, List<Country> countryList) {
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.userList = userList;
        this.connectionList = connectionList;
        this.countryList = countryList;
    }

    public ServiceProvider(String name, Admin admin) {
        this.name = name;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
