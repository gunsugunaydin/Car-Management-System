package org.gunsugunaydin.SpringCarUser.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.gunsugunaydin.SpringCarUser.models.Authority;
import org.gunsugunaydin.SpringCarUser.models.Car;
import org.gunsugunaydin.SpringCarUser.models.User;
import org.gunsugunaydin.SpringCarUser.services.IAuthorityService;
import org.gunsugunaydin.SpringCarUser.services.ICarService;
import org.gunsugunaydin.SpringCarUser.services.IUserService;
import org.gunsugunaydin.SpringCarUser.util.constants.Privileges;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class SeedData implements CommandLineRunner {

    private final ICarService carService;
    private final IUserService userService;
    private final IAuthorityService authorityService;

    public SeedData(ICarService carService, IUserService userService, IAuthorityService authorityService) {
        this.carService = carService;
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @Override
    public void run(String... args) throws Exception {
        
        if (userService.findAll().isEmpty()) {
            
            for(Privileges auth: Privileges.values()){
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivilege());
            authorityService.save(authority);
            }
            
            Set<Authority> userAuthorities = new HashSet<>();
            authorityService.findById(Privileges.ACCESS_USER_ENDPOINTS.getId()).ifPresent(userAuthorities::add);

            User user1 = new User(null, "user1@user1.com", "Günsu", "Günaydın", "password1", "1234567890", LocalDateTime.now(), LocalDate.of(1990, 1, 1), userAuthorities, null);
            User user2 = new User(null, "user2@user2.com", "Göksu", "Günaydın", "password2", "0987654321", LocalDateTime.now(), LocalDate.of(1992, 2, 2), userAuthorities, null);
            User user3 = new User(null, "user3@user3.com", "Lila", "Çiçek", "password3", "1122334455", LocalDateTime.now(), LocalDate.of(1994, 3, 3), userAuthorities, null);
            User user4 = new User(null, "user4@user4.com", "Latife", "Sedef", "password4", "5566778899", LocalDateTime.now(), LocalDate.of(1996, 4, 4), userAuthorities, null);

            userService.save(user1);
            userService.save(user2);
            userService.save(user3);
            userService.save(user4);

            Car car1 = new Car(null, "Toyota", "Corolla", 2020, "Beyaz", 200000, 15000, "Benzin", "Otomatik", 132, 4, "Sedan", user1);
            Car car2 = new Car(null, "Honda", "Civic", 2019, "Siyah", 180000, 20000, "Dizel", "Manuel", 120, 4, "Sedan", user2);
            Car car3 = new Car(null, "Tesla", "Model 3", 2021, "Kırmızı", 500000, 10000, "Elektrik", "Otomatik", 283, 4, "Sedan", user3);
            Car car4 = new Car(null, "Ford", "Focus", 2018, "Mavi", 160000, 30000, "Benzin", "Manuel", 150, 4, "Hatchback", user4);
            Car car5 = new Car(null, "BMW", "X5", 2022, "Gri", 600000, 5000, "Dizel", "Otomatik", 340, 4, "SUV", user1);

            carService.save(car1);
            carService.save(car2);
            carService.save(car3);
            carService.save(car4);
            carService.save(car5);
        }
    }
}
