package com.ne.template.serviceImpls;

import com.ne.template.exceptions.BadRequestAlertException;
import com.ne.template.exceptions.NotFoundException;
import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.User;
import com.ne.template.repositories.IUserRepository;
import com.ne.template.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class UserServiceImpl implements IUserService {
    public final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user was not found with id: " + id));
    }

    @Override
    public User createUser(User user) {

        validateNewRegistration(user);
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> userOptional = this.userRepository.findById(user.getId());
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User with id: " + user.getId().toString() + "is not found");
        }
        User userToUpdate = userOptional.get();
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());
        return this.userRepository.save(userToUpdate);

    }

    @Override
    public Boolean deleteUser(UUID id) {
        User userToDelete = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id.toString() + "is not found"));
        this.userRepository.delete(userToDelete);
        return true;

    }

    @Override
    public User getUserByEmail(String email) {
        User userByEmail = userRepository.findUserByEmail(email).orElseThrow(() -> new NotFoundException("User with email: " + email + "is not found"));
        return userByEmail;
    }

    @Override
    public User getLoggedInUser() throws ResourceNotFoundException {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser")
            throw new BadRequestAlertException("You are not logged in, try to log in");

        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException(String.format("User with email %s is not found", email)));
    }

    @Override
    public boolean isNotUnique(User user) {
        return userRepository.findUserByEmail(user.getEmail()).isPresent();

    }
    public void validateNewRegistration(User user) {
        if(isNotUnique(user)){
            throw new BadRequestAlertException("User with email " + user.getEmail() + " already exists");
        }

    }
}
