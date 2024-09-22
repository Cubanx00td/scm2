package com.scm.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.scm.entities.Providers;
import com.scm.scm.entities.User;
import com.scm.scm.helper.AppConstants;
import com.scm.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

    @Autowired
    private UserRepo userRepo;

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("OAuthAuthenticationSuccessHandler");

               var oauth2AuthernticationToken = (OAuth2AuthenticationToken)authentication;

               String authorizedClientRegistrationId = oauth2AuthernticationToken.getAuthorizedClientRegistrationId();

               logger.info(authorizedClientRegistrationId);

                var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();

                oauthUser.getAttributes().forEach((key, value) -> {

                });

                User user = new User();
                user.setUserId(UUID.randomUUID().toString());
                user.setRoleList(List.of(AppConstants.ROLE_USER));
                user.setEmailVerified(true);
                user.setEnabled(true);
                user.setPassword("dummy");


               if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    user.setEmail(oauthUser.getAttribute("email").toString());
                    user.setName(oauthUser.getAttribute("name").toString());
                    user.setProfilePic(oauthUser.getAttribute("picture").toString());
                    user.setProviderUserId(oauthUser.getName());
                    user.setProvider(Providers.GOOGLE);
                    user.setAbout("this account is created using google");
               }

               else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";

                    String picture = oauthUser.getAttribute("avatar_url").toString();

                    String name = oauthUser.getAttribute("login").toString();

                    String providerUserId = oauthUser.getName();

                    user.setEmail(email);
                    user.setProfilePic(picture);
                    user.setName(name);
                    user.setProviderUserId(providerUserId);
                    user.setProvider(Providers.GITHUB);
                    user.setAbout("this account is created using github");
               }
               else{
                    logger.info("OAuthAuthenticationSuccessHandler: Unknown Provider");
               }

               User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);

               if(user2 == null){
                userRepo.save(user);
                logger.info("User Saved");
               }

               new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");


    }
    
}
