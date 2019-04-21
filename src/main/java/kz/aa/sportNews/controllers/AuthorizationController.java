package kz.aa.sportNews.controllers;

import kz.aa.sportNews.model.User;
import kz.aa.sportNews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<User> userExists = userService.findUserByLogin(user.getLogin());
        if (userExists.isPresent()) {
            bindingResult
                    .rejectValue("login", "error.user",
                            "Пользователь с таким именем уже существует");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            user.setIsActive(true);
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Регистрация прошла успешно, сейчас вы будете перенаправлены");
            modelAndView.addObject("user", new User());


            modelAndView.setViewName("redirect:/login");
        }

        return modelAndView;
    }
}
