package com.scm.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm.entities.User;
import com.scm.scm.helper.Message;
import com.scm.scm.helper.MessageType;
import com.scm.scm.repositories.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session){
        User user = userRepo.findByEmailToken(token).orElse(null);

        if(user != null){
            //user fetch hua hai
            if(user.getEmailToken().equals(token)){
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder().type(MessageType.green).content("Email verified successfully").build());
                return "verified_page";
            }
            session.setAttribute("message", Message.builder().type(MessageType.red).content("Email not verified! Token is not valid").build());
            return "error_page";
        }
        session.setAttribute("message", Message.builder().type(MessageType.red).content("Email not verified! Token is not valid").build());
        return "error_page";
    }
}
