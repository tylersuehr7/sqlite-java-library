package models;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public class User extends Entity {
    private String firstName;
    private String lastName;
    private String username;


    public User() {}

    @Override
    public String toString() {
        return "{id: " + getId() + ", first: " + firstName + ", last: " + lastName + ", user: " + username + "}";
    }

    @Override
    public String getId() {
        return (String)super.getId();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}