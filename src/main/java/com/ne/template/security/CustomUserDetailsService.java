package com.ne.template.security;

import com.ne.template.exceptions.BadRequestAlertException;
import com.ne.template.models.User;
import com.ne.template.repositories.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;
    public  CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public UserDetails loadByUserId(UUID id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found with id: "+ id));
        return UserPrincipal.create(user);
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws BadRequestAlertException {
        User user = userRepository.findUserByUsernameOrEmail(s, s).orElseThrow(() -> new UsernameNotFoundException("user not found with email or mobile of " + s));
        return UserPrincipal.create(user);

    }
}
