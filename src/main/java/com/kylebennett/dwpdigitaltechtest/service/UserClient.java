package com.kylebennett.dwpdigitaltechtest.service;

import com.kylebennett.dwpdigitaltechtest.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserClient {

    Collection<User> getAllUsers();

    Collection<User> getAllUsersFromCity(String city);

    Optional<User> getUserById(int id);
}
