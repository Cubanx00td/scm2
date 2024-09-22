package com.scm.scm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{

    //custom finder method
    Page<Contact> findByUser(User user, Pageable pageable);

    //custom query method
    @Query("SELECT c FROM Contact c WHERE c.user.userId=:userId")
    List<Contact> findByUserId(@Param("userId") String userId);
    Optional<Contact> findByContactId(String contactId);
    Page<Contact> findByUserAndNameContaining(User user, String nameKeyword, Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user, String emailKeyword, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneKeyword, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Contact c WHERE c.user.userId =:userId")
    Integer getNumberOfContacts(String userId);
}
