package org.example.jdbc.dao;

import org.example.model.User;

import java.util.Set;

public interface UsersDAO {

    boolean createUser(User user);
    boolean updateUser(User user);
    boolean deleteUserById(int id);
    User getUserById(int id);
    Set<User> getAllUser();

    User getUserByEmail(String email);


    boolean activateUser(String email);

}
