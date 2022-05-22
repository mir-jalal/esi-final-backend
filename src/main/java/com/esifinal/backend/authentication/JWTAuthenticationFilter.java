package com.esifinal.backend.authentication;

import com.esifinal.backend.model.dto.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final MessageDigest messageDigest;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    @Value("${app.security.header_string}")
    private String headerString;

    @Value("${app.security.token_prefix}")
    private String tokenPrefix;

    @Value("${app.security.secret_key}")
    private String secret;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationRequest auth = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    auth.getUsername(),
                    auth.getPassword(),
                    new ArrayList<>()
            );

            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        Date date = new Date();
        String token = Jwts.builder()
                .setId(Arrays.toString(messageDigest.digest(secret.getBytes(StandardCharsets.UTF_8))))
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + 3_600_000L))
                .claim("authorities", authResult.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
        response.addHeader(headerString, tokenPrefix + token);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
