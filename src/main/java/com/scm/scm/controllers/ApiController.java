package com.scm.scm.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.scm.scm.entities.Contact;
import com.scm.scm.services.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    //get contact user
    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId){
        return contactService.getByContactId(contactId);
    }

}
