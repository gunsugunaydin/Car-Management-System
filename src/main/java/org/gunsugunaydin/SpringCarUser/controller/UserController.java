package org.gunsugunaydin.SpringCarUser.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.dtos.LoginDTO;
import org.gunsugunaydin.SpringCarUser.dtos.TokenDTO;
import org.gunsugunaydin.SpringCarUser.dtos.UserDTO;
import org.gunsugunaydin.SpringCarUser.dtos.UserViewDTO;
import org.gunsugunaydin.SpringCarUser.models.Car;
import org.gunsugunaydin.SpringCarUser.models.User;
import org.gunsugunaydin.SpringCarUser.services.ICarService;
import org.gunsugunaydin.SpringCarUser.services.IUserService;
import org.gunsugunaydin.SpringCarUser.services.TokenService;
import org.gunsugunaydin.SpringCarUser.util.constants.ErrorMessages;
import org.gunsugunaydin.SpringCarUser.util.constants.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "User Controller", description = "Controller for account management" )
@Slf4j
public class UserController {
   
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICarService carService;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/token", produces = "application/json", consumes = "application/json" )
    @ApiResponse(responseCode = "401", description = "Unauthorized: Email or password incorrect")
    @ApiResponse(responseCode = "403", description = "Forbiden: Access to this resource is denied")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a token")
    public ResponseEntity<TokenDTO> token(@Valid @RequestBody LoginDTO loginDto) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            return ResponseEntity.ok(new TokenDTO(tokenService.generateToken(authentication)));        
        } catch (Exception e) {
            log.debug(ErrorMessages.TOKEN_GENERATION_ERROR.toString() + ": " + e.getMessage());
            return new ResponseEntity<>(new TokenDTO(null), HttpStatus.BAD_REQUEST);
        }
    }
    

    @PostMapping(value = "/users/add", produces = "application/json", consumes = "application/json")
    @ApiResponse(responseCode = "200", description = "User added")
    @Operation(summary = "Add a new user")
    public ResponseEntity<Map<String, String>> addUser(@Valid @RequestBody UserDTO userDto) {
        try {
            userService.addNewUser(userDto);
    
            Map<String, String> response = new HashMap<>();
            response.put("message", SuccessMessages.USER_SUCCESSFULLY_ADDED.toString());
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.debug(ErrorMessages.USER_ADDITION_ERROR.toString() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Enter valid input"));
        } 
    }

    
    @GetMapping(value = "/users/profile",produces = "application/json")
    @ApiResponse(responseCode = "200", description = "User's own profile")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "View profile")
    public UserViewDTO profile(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalAccount = userService.findByEmail(email);
        User user = optionalAccount.get();
        UserViewDTO profile = new UserViewDTO(user.getId(), user.getEmail(), user.getName(),user.getSurname(),user.getPhoneNumber(),user.getBirthDate());
        return profile;
    }


    @PutMapping(value ="/users/profile/update", produces = "application/json", consumes = "application/json")
    @ApiResponse(responseCode = "200", description = "User updated")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "Update user profile")
    public ResponseEntity<Map<String, String>> updateUserProfile(@Valid @RequestBody UserDTO userDto, Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = userService.findByEmail(email);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userService.updateUser(userDto, user);

            Map<String, String> response = new HashMap<>();
            response.put("message", SuccessMessages.USER_SUCCESSFULLY_UPDATED.toString());
            return ResponseEntity.ok(response);
    
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ErrorMessages.USER_NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    

    @DeleteMapping(value="users/delete", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "User deleted")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "Delete user")
    public ResponseEntity<Map<String, String>> deleteUser(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // İlk, kullanıcının tüm arabalarını siliyorum.
            List<Car> cars = carService.findByUser(user);
            for (Car car : cars) {
                carService.deleteById(car.getId());
            }

            // Sonra kullanıcı
            userService.delete(user);   
                
            Map<String, String> response = new HashMap<>();
            response.put("message", SuccessMessages.USER_SUCCESSFULLY_DELETED.toString());
            return ResponseEntity.ok(response);

        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ErrorMessages.USER_NOT_FOUND.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

