package com.esifinal.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserDetailsService userDetailsService;
    private final DaoAuthenticationProvider authenticationProvider;

    @Autowired
    public AuthenticationManagerImpl(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = new DaoAuthenticationProvider();
        this.authenticationProvider.setPasswordEncoder(passwordEncoder);
        this.authenticationProvider.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName() == null || authentication.getCredentials() == null) {
            throw new BadCredentialsException("Credentials are null");
        }

        String username = authentication.getName();
        try {
            authenticationProvider.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Cannot authenticate");
        }

        UserDetails user = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());

    }
}
