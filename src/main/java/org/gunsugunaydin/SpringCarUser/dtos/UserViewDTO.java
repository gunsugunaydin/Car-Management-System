package org.gunsugunaydin.SpringCarUser.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


//Kullanıcının profilinde görebilecekleri. "Password", registration date ve authority yok. 
@Getter
@Setter
@AllArgsConstructor
public class UserViewDTO {

    private long id;

    @Email
    private String email;

    private String name;

    private String surname;

    private String phoneNumber;
    
    private LocalDate birthDate;
}
