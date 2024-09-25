package com.scm.scm.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;


@Component
public class Helper {

    @Value("${server.baseUrl}")
    private String baseUrl;
    
    public static String getEmailOfLoggedInUser(Authentication authentication){

        if(authentication instanceof OAuth2AuthenticationToken){
            var oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String username = "";
        
            //signin with google
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("Google signin");
                username = oauth2User.getAttribute("email").toString();
            }

            //signin with github
            else if(clientId.equalsIgnoreCase("github")){
                System.out.println("Github signin");
                String email = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString() + "@gmail.com";        
                username = email;
            }
            return username;
        }
        else{
            //normal email and password
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
    }

    public String getEmailVerificationLink(String emailToken){
        String link = this.baseUrl + "/auth/verify-email?token=" + emailToken;
        return link;
    }
}
