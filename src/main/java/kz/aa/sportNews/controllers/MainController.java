package kz.aa.sportNews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainForAdmin(Model model) {

//        Optional<User> user = userService.findCurrentUser();
//
//        List<CustomerOrder> customerOrderList = customerOrderService.findAllByConfirmedIsTrue();

//        model.addAttribute("orders",customerOrderList);
//        model.addAttribute("user", Objects.requireNonNullElseGet(user, User::new));
//        model.addAttribute("dombra", new Dombra());
//        model.addAttribute("cap", new Cap());
//        model.addAttribute("genderTypes", Gender.values());
//        model.addAttribute("colorEnum", ColorEnum.values());
//        model.addAttribute("sizeEnum", SizeEnum.values());
//        model.addAttribute("materialEnum", MaterialEnum.values());

        return "index";
    }

}
