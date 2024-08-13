package org.gunsugunaydin.SpringCarUser.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.gunsugunaydin.SpringCarUser.dtos.UserDTO;
import org.gunsugunaydin.SpringCarUser.models.Authority;
import org.gunsugunaydin.SpringCarUser.models.User;
import org.gunsugunaydin.SpringCarUser.repositories.UserRepository;
import org.gunsugunaydin.SpringCarUser.util.constants.Privileges;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


@Service
public class UserServiceImpl implements IUserService,UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IAuthorityService authorityService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, IAuthorityService authorityService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
    }

    @Override
    public Optional<User> findById(Long id){
       return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
       return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        if (user.getId() == null){
            user.setRegistrationDate(LocalDateTime.now());
        }         
        if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
                Authority defaultAuthority = authorityService.findById(Privileges.ACCESS_USER_ENDPOINTS.getId())
                                            .orElseThrow(() -> new RuntimeException("Default authority (ACCESS_USER_ENDPOINTS) not found"));
                user.getAuthorities().add(defaultAuthority);
        }
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User addNewUser(UserDTO userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setBirthDate(userDto.getBirthDate());
        return save(user);
    }

    @Override
    @Transactional
    public User updateUser(UserDTO userDto, User user) {
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null && !userDto.getName().equals(user.getName())) {
            user.setName(userDto.getName());
        }
        if (userDto.getSurname() != null && !userDto.getSurname().equals(user.getSurname())) {
            user.setSurname(userDto.getSurname());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getPhoneNumber() != null && !userDto.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getBirthDate() != null && !userDto.getBirthDate().equals(user.getBirthDate())) {
            user.setBirthDate(userDto.getBirthDate());
        }
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void delete(User user){
        userRepository.delete(user);   
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
 
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
