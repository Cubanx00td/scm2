package com.scm.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm.entities.User;
import com.scm.scm.repositories.UserRepo;
import com.scm.scm.services.EmailService;
import com.scm.scm.services.UserService;

import jakarta.validation.constraints.Email;

import com.scm.scm.helper.AppConstants;
import com.scm.scm.helper.Helper;
import com.scm.scm.helper.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;


    @Override
    public com.scm.scm.entities.User saveUser(com.scm.scm.entities.User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(AppConstants.ROLE_USER));

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);

        User savedUser = userRepo.save(user);
        String emailLink = Helper.getEmailVerificationLink(emailToken);
        emailService.sendEmail(savedUser.getEmail(), "Verify Your Email - Smart Contact Manager", emailLink);

        return savedUser;
    }

    @Override
    public Optional<com.scm.scm.entities.User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<com.scm.scm.entities.User> updateUser(com.scm.scm.entities.User user) {
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        //save the user in database
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<com.scm.scm.entities.User> getAllUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUser'");
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user;
    }
    
}
