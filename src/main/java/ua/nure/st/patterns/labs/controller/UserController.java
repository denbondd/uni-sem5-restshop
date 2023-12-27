package ua.nure.st.patterns.labs.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.st.patterns.labs.dao.UserDao;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/signup")
    public boolean signup(@RequestHeader("login") String login, @RequestHeader("password") String password, @RequestBody Integer roleId) {
        return userDao.signup(login, password, roleId);
    }

}
