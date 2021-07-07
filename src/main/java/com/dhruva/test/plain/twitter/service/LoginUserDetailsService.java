package com.dhruva.test.plain.twitter.service;

import com.dhruva.test.plain.twitter.model.LoginRequest;
import com.dhruva.test.plain.twitter.model.LoginUserDetails;
import com.dhruva.test.plain.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public LoginUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        LoginRequest loginRequest = userRepository.findByUserId(userId)
                                                  .orElseThrow(() -> new UsernameNotFoundException("Invalid userId supplied."));
        return LoginUserDetails.builder()
                               .userName(loginRequest.getUserId())
                               .build();
    }
}
