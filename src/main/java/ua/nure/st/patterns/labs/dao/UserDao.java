package ua.nure.st.patterns.labs.dao;

import ua.nure.st.patterns.labs.entity.User;

public interface UserDao {

    User login(String login, String password);
    boolean signup(String login, String password, Integer roleId);
}
