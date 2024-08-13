package org.gunsugunaydin.SpringCarUser.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


//Yeni kullanıcı girişi için kullanıcının görecekleri(add user requestbody).
//Kullanıcı register olurken generated id giremez. Kayıt olma tarihini giremez. Authorithy giremez.
@Getter
@Setter
public class UserDTO {
    
    @Email
    private String email;

    private String name;

    private String surname;

    @Size(min = 8, max = 20)
    private String password;

    private String phoneNumber;
    
    private LocalDate birthDate;
}
