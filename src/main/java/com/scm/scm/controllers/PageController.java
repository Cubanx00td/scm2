package com.scm.scm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.scm.entities.User;
import com.scm.scm.forms.UserForm;
import com.scm.scm.helper.Message;
import com.scm.scm.helper.MessageType;
import com.scm.scm.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;





@Controller
public class PageController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String indexString() {
        return "redirect:/home";
    }
    
    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("Home Page Handler");
        model.addAttribute("page", "home");
        return "home";
    }

    //about
    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("isLogin", true);
        model.addAttribute("page", "about");
        System.out.println("about page handler");
        return "about";
    }

    @RequestMapping("/services")
    public String services(Model model) {
        System.out.println("service page handler");
        model.addAttribute("page", "services");
        return "services";
    }
    

    @RequestMapping("/login")
    public String login() {
        System.out.println("login page handler");
        return "login";
    }
    

    @RequestMapping("/register")
    public String register(Model model) {
        System.out.println("register page handler");
        UserForm userForm = new UserForm();
        // userForm.setName("Durgesh");
        model.addAttribute("userForm", userForm);
        return "register";
    }
    

    @RequestMapping("/contact")
    public String contact(Model model) {
        System.out.println("contact page handler");
        model.addAttribute("page", "contact");
        return "contact";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, RedirectAttributes redirectAttributes){
        System.out.println("Processing registration");

        //fetch form data
        System.out.println(userForm);

        //validate
        if(rBindingResult.hasErrors()){
            return "register";
        }

        //save
        //userservice
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .profilePic("https://imgs.search.brave.com/GbCdC7mgiFvBTu6bnsfQAypLsf3LDOm17CYHzGNSpyA/rs:fit:500:0:0/g:ce/aHR0cHM6Ly93d3cu/cG5naXRlbS5jb20v/cGltZ3MvbS8yMi0y/MjM5NjhfZGVmYXVs/dC1wcm9maWxlLXBp/Y3R1cmUtY2lyY2xl/LWhkLXBuZy1kb3du/bG9hZC5wbmc")
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://imgs.search.brave.com/GbCdC7mgiFvBTu6bnsfQAypLsf3LDOm17CYHzGNSpyA/rs:fit:500:0:0/g:ce/aHR0cHM6Ly93d3cu/cG5naXRlbS5jb20v/cGltZ3MvbS8yMi0y/MjM5NjhfZGVmYXVs/dC1wcm9maWxlLXBp/Y3R1cmUtY2lyY2xl/LWhkLXBuZy1kb3du/bG9hZC5wbmc");
        user.setEnabled(false);
  
        User savedUser = userService.saveUser(user);
        System.out.println("User Saved");

        //message 
        Message message = Message.builder().content("Registration Successful. \n A verification link has been sent to the provided email.").type(MessageType.green).build();

        redirectAttributes.addFlashAttribute("message", message);
        
        //redirect
        return "redirect:/register";
    }



    // @PostMapping("/do-register")
    // public String processRegister(@ModelAttribute UserForm userForm, HttpServletRequest httpRequest){
    //     System.out.println("Processing registration");

    //     //fetch form data
    //     System.out.println(userForm);
    //     //validate
    //     //save
    //     //userservice
    //     // User user = User.builder()
    //     // .name(userForm.getName())
    //     // .email(userForm.getEmail())
    //     // .password(userForm.getPassword())
    //     // .about(userForm.getAbout())
    //     // .phoneNumber(userForm.getPhoneNumber())
    //     // .profilePic("https://imgs.search.brave.com/GbCdC7mgiFvBTu6bnsfQAypLsf3LDOm17CYHzGNSpyA/rs:fit:500:0:0/g:ce/aHR0cHM6Ly93d3cu/cG5naXRlbS5jb20v/cGltZ3MvbS8yMi0y/MjM5NjhfZGVmYXVs/dC1wcm9maWxlLXBp/Y3R1cmUtY2lyY2xl/LWhkLXBuZy1kb3du/bG9hZC5wbmc")
    //     // .build();

    //     User user = new User();
    //     user.setName(userForm.getName());
    //     user.setEmail(userForm.getEmail());
    //     user.setPassword(userForm.getPassword());
    //     user.setAbout(userForm.getAbout());
    //     user.setPhoneNumber(userForm.getPhoneNumber());
    //     user.setProfilePic("https://imgs.search.brave.com/GbCdC7mgiFvBTu6bnsfQAypLsf3LDOm17CYHzGNSpyA/rs:fit:500:0:0/g:ce/aHR0cHM6Ly93d3cu/cG5naXRlbS5jb20v/cGltZ3MvbS8yMi0y/MjM5NjhfZGVmYXVs/dC1wcm9maWxlLXBp/Y3R1cmUtY2lyY2xl/LWhkLXBuZy1kb3du/bG9hZC5wbmc");
  

    //     User savedUser = userService.saveUser(user);
    //     System.out.println("User Saved");

    //     //message 
    //     HttpSession session = httpRequest.getSession();

    //     session.setAttribute("message", "Registration Successful");
        
    //     //redirect
    //     return "redirect:/register";
    // }
    
}
