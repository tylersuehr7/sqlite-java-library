package repositories.users;
import models.User;
import repositories.ListCallback;
import repositories.SingleCallback;

/**
 * Copyright 2017 Tyler Suehr
 * Created by tyler on 8/23/2017.
 */
public interface IUserRepository {
    void saveUser(User user);
    void updateUser(User user);
    void removeUser(User user);
    void findUserById(String userId, SingleCallback<User> callback);
    void findAllUsers(ListCallback<User> callback);
}