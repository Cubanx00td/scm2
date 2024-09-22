package com.scm.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;
import com.scm.scm.forms.ContactForm;
import com.scm.scm.forms.ContactSearchForm;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.helper.Helper;
import com.scm.scm.helper.Message;
import com.scm.scm.helper.MessageType;
import com.scm.scm.services.ContactService;
import com.scm.scm.services.ImageService;
import com.scm.scm.services.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ImageService imageService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("page", "add");
        return "user/add_contact";
    }

    @PostMapping("/process-contact")
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Authentication authentication, RedirectAttributes redirectAttributes, Model model){

        //validate form

        if(bindingResult.hasErrors()){

            //message 
            Message message = Message.builder().content("Please correct the following errors").type(MessageType.red).build();

            
            model.addAttribute("message", message);
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User userByEmail = userService.getUserByEmail(username);

        Contact contact = new Contact();

        //process contact picture
        logger.info("file information: {}", contactForm.getProfileImage().getOriginalFilename());

        String filename = null;
        String fileURL = null;

        if(contactForm.getProfileImage().isEmpty()){
            fileURL = "https://freesvg.org/img/abstract-user-flat-4.png";
            
        }
        else{
            filename = UUID.randomUUID().toString();
            fileURL = imageService.uploadImage(contactForm.getProfileImage(), filename);
            
            contact.setCloudinaryImagePublicId(filename);
        }

        contact.setName(contactForm.getName());
        contact.setAddress(contactForm.getAddress());
        contact.setEmail(contactForm.getEmail());
        contact.setDescription(contactForm.getDescription());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setLinkedInLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setFavourite(contactForm.isFavourite());
        contact.setUser(userByEmail);
        contact.setPicture(fileURL);
        contactService.save(contact);

        //set message to be displayed
        //message 
        Message message = Message.builder().content("Contact Added Successfully").type(MessageType.green).build();

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/user/contacts/add";
    }

    @RequestMapping(value="", method=RequestMethod.GET)
    public String viewContacts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "" + AppConstants.PAGE_SIZE) int size,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Authentication authentication, 
        Model model){

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        model.addAttribute("page", "view");

        return "user/view_all_contacts";
    }

    @RequestMapping("/search")
    public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
        @RequestParam(value = "size", defaultValue = "" + AppConstants.PAGE_SIZE) int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction,
        Model model,
        Authentication authentication
        ){

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getKeyword());

        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact = contactService.searchByName(contactSearchForm.getKeyword(), size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact = contactService.searchByEmail(contactSearchForm.getKeyword(), size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("phoneNumber")){
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getKeyword(), size, page, sortBy, direction, user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("none")){
            model.addAttribute("message", new Message("Please select a field for searching", MessageType.red));
            pageContact = contactService.getByUser(user, page, size, sortBy, direction);
            model.addAttribute("pageContact", pageContact);
            return "user/view_all_contacts";
        }
        
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("page", "view");

        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, RedirectAttributes redirectAttributes){
        contactService.delete(contactId);
        logger.info("Contact Id: {} deleted", contactId);
        redirectAttributes.addFlashAttribute("message", new Message("Contact Deleted Successfully", MessageType.red));
        return "redirect:/user/contacts";
    }

    @RequestMapping("/update/{contactId}")
    public String updateView(@PathVariable String contactId, Model model){
        Contact contact = contactService.getByContactId(contactId);

        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedInLink());


        contactForm.setPicture(contact.getPicture());
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update-contact/{contactId}", method = RequestMethod.POST)
    public String updateContact( @PathVariable String contactId, @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "user/update_contact_view";
        }
        
        //update the contact
        var contact = contactService.getByContactId(contactId);

        contact.setContactId(contactId);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedinLink());
        
        //process image
        if(contactForm.getProfileImage() != null && !contactForm.getProfileImage().isEmpty()){
            String filename = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getProfileImage(), filename);

            contact.setCloudinaryImagePublicId(filename);
            contact.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        }

        
        var updatedContact = contactService.update(contact);
        redirectAttributes.addFlashAttribute("message", new Message("Contact Updated Successfully", MessageType.green));
        
        return "redirect:/user/contacts/update/" + contactId;
    }

    
}
