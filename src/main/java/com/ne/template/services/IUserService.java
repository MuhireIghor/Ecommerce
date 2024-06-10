package com.ne.template.services;

import com.ne.template.exceptions.ResourceNotFoundException;
import com.ne.template.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    public List<User> getAllUsers();
    public User getUserById(UUID id);
    public User createUser(User user);
    public User updateUser(User user);
    public Boolean deleteUser(UUID id);
    public User getUserByEmail(String email) ;
    public User getLoggedInUser() throws ResourceNotFoundException;
    public boolean isNotUnique(User user) ;

}
