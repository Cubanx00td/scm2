package com.scm.scm.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.scm.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
    //extra methods
    //custom query methods
    //custom finder methods

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmailToken(String token);
    
}
