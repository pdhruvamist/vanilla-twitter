package com.dhruva.test.plain.twitter.controller;

import com.dhruva.test.plain.twitter.model.LoginRequest;
import com.dhruva.test.plain.twitter.model.LoginResponse;
import com.dhruva.test.plain.twitter.repository.UserRepository;
import com.dhruva.test.plain.twitter.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class LoginAuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder encoder;

    public LoginAuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = JwtTokenUtils.generateJwtToken(authentication);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setJwtToken(jwtToken);

        log.info("Successfully authenticated user:{}", loginRequest.getUserId());

        return ResponseEntity.ok(loginResponse);
    }
}
