package kz.aa.sportNews.controllers;

import kz.aa.sportNews.model.Role;
import kz.aa.sportNews.model.User;
import kz.aa.sportNews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthorizationController {

    private final UserService userService;

    @Autowired
    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String createNewUser(@RequestParam("name") String name,
                                      @RequestParam("lastName") String lastName,
                                      @RequestParam("login") String login,
                                      @RequestParam("password") String password,
                                @RequestParam("key") String key,
                                Model model) {

        Optional<User> userExists = userService.findUserByLogin(login);

        if (userExists.isPresent()) {
            model.addAttribute("response", "Уже существует пользователь с таким логином");
            return "/registration.html";
        }

        if (key.equals("newsecretpassw0rd")) {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setPassword(password);
            user.setLastName(lastName);
            user.setIsActive(true);
            user.setRole(Role.ADMIN);

            userService.saveUser(user);
            model.addAttribute("response", "Регистрация прошла успешно");
            return "/registration.html";
        } else {

            model.addAttribute("response", "Ошибка");
            return "/registration.html";
        }
    }
}
