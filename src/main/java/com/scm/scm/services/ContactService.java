package com.scm.scm.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

public interface ContactService {

    //save contact
    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contacts
    List<Contact> getAll();

    //get contact by id
    Contact getByContactId(String id);

    //delete contact
    void delete(String id);

    //search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String direction, User user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String direction, User user);

    Page<Contact> searchByPhoneNumber(String phoneKeyword,  int size, int page, String sortBy, String direction, User user);

    //get contacts by user id
    List<Contact> getByUserId(String userId);

    //get contacts by user
    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);

    Integer getNumberOfContacts(String userId);
}
