package com.kylebennett.dwpdigitaltechtest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchResult {

    private final List<String> errorMessages = new ArrayList<>();
    private Set<User> users;

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
