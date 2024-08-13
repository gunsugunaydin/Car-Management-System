package org.gunsugunaydin.SpringCarUser.services;

import java.util.List;
import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.dtos.UserDTO;
import org.gunsugunaydin.SpringCarUser.models.User;


public interface IUserService {
    
    public Optional<User> findById(Long id);
    public Optional<User> findByEmail(String email);
    public List<User> findAll();
    public User save(User user);
    public User addNewUser(UserDTO userDto);
    public User updateUser(UserDTO userDto, User user);
    public void delete(User user);
}
