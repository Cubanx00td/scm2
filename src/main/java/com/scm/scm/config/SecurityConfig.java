package com.scm.scm.config;

import java.io.IOException;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import com.scm.scm.helper.Message;
import com.scm.scm.helper.MessageType;
import com.scm.scm.repositories.UserRepo;
import com.scm.scm.services.impl.CustomUserDetailsService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class SecurityConfig {

    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        //configuration
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        
        httpSecurity.formLogin(form -> {
            form.loginPage("/login").loginProcessingUrl("/authenticate").defaultSuccessUrl("/user/profile").usernameParameter("email").passwordParameter("password");
            form.failureHandler(new AuthenticationFailureHandler() {

                @Override
                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                        AuthenticationException exception) throws IOException, ServletException {
                        if(exception instanceof DisabledException){
                            //user is disabled
                            HttpSession session = request.getSession();
                            session.setAttribute("message", Message.builder().content("User is disabled").type(MessageType.red).build());
                            response.sendRedirect("/login");
                        }
                        else {
                            response.sendRedirect("/login?error=true");
                        }
                }
                
            });
        });

        httpSecurity.csrf(csrf-> csrf.disable());

        httpSecurity.logout(logoutForm -> 
            logoutForm.logoutUrl("/do-logout")
            .logoutSuccessUrl("/login?logout")
        );

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(oAuthAuthenticationSuccessHandler);
        });

        
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //user create and login using java with in memory service
    
    // public UserDetailsService userDetailsService(){
    //     UserDetails user = User
    //     .withDefaultPasswordEncoder()
    //     .username("user")
    //     .password("user")
    //     .roles("USER")
    //     .build();

    //     return new InMemoryUserDetailsManager(user);
    // }
}
