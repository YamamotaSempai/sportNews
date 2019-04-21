package kz.aa.sportNews.service;

import kz.aa.sportNews.model.User;

import java.util.List;
import java.util.Optional;



public interface UserService {
    Optional<User> findUserByLogin(String login);

    List<User> findAll();

    User saveUser(User user);

    Optional<User> findCurrentUser();
}
