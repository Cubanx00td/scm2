package com.scm.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm.helper.Helper;
import com.scm.scm.services.ContactService;
import com.scm.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    
    //user dashboard
    @RequestMapping(value="/dashboard", method=RequestMethod.GET)
    public String userDashboard(Model model,
        Authentication authentication) {

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        int numberOfContacts = contactService.getNumberOfContacts(user.getUserId());
        model.addAttribute("numberOfContacts", numberOfContacts);
        model.addAttribute("page", "dashboard");
        return "user/dashboard";
    }
    
    //user add contact

    //user view contact

    //user edit contact

    //user profile
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String userProfile(Model model) {
        model.addAttribute("page", "profile");
        return "user/profile";
    }

    @RequestMapping("/feedback")
    public String feedback(Model model){
        model.addAttribute("page", "feedback");
        return "user/feedback";
    }

}
