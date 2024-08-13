package org.gunsugunaydin.SpringCarUser.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.dtos.CarDTO;
import org.gunsugunaydin.SpringCarUser.dtos.CarViewDTO;
import org.gunsugunaydin.SpringCarUser.models.Car;
import org.gunsugunaydin.SpringCarUser.models.User;
import org.gunsugunaydin.SpringCarUser.services.ICarService;
import org.gunsugunaydin.SpringCarUser.services.IUserService;
import org.gunsugunaydin.SpringCarUser.util.constants.ErrorMessages;
import org.gunsugunaydin.SpringCarUser.util.constants.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1")
@Tag(name = "Car Controller", description = "Controller for car management" )
@Slf4j
public class CarController {

    @Autowired
    private ICarService carService;

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/cars/add", produces = "application/json", consumes = "application/json")
    @ApiResponse(responseCode = "200", description = "Car added")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "Add a new car")
    public ResponseEntity<Map<String,String>> addCar(@Valid @RequestBody CarDTO carDto, Authentication authentication){
        try {
            String email = authentication.getName();
            Optional<User> optionalUser = userService.findByEmail(email);

            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                carService.addNewCar(carDto, user);
                
                Map<String, String> response = new HashMap<>();
                response.put("message",SuccessMessages.CAR_SUCCESSFULLY_ADDED.toString());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "User not found"));

        } catch (Exception e) {
            log.debug(ErrorMessages.CAR_ADDITION_ERROR.toString() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Error adding car"));
        }
    }


    @GetMapping(value = "/cars/list", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "List of cars")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "List all cars of a user")
    public ResponseEntity<List<CarViewDTO>> listCars(Authentication authentication) {
        String email = authentication.getName();
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<CarViewDTO> cars = new ArrayList<>();
            List<Car> carsOfUser = carService.findByUser(user);
            for (Car car : carsOfUser) {
                cars.add(new CarViewDTO(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getColor(),
                                        car.getPrice(), car.getMileage(), car.getFuelType(),
                                        car.getTransmissionType(), car.getHorsepower(), car.getDoorCount(),
                                        car.getBodyType()));
            }
            return new ResponseEntity<>(cars, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    }

    
    @PutMapping(value = "/cars/{car_id}/update", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Car updated")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person Token Error: You are not the authorized person OR price, mileage < 0 OR door count is not between 1 and 10.")
    @ApiResponse(responseCode = "404", description = "Car not found")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "Update a car of a user")
    public  ResponseEntity<CarViewDTO> updateCar(@Valid @PathVariable Long car_id, @RequestBody CarDTO carDto) {
        Optional<Car> optionalCar = carService.findById(car_id);

        if (!optionalCar.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 

        Car car = optionalCar.get();      
        Car updatedCar = carService.updateCar(carDto, car);
        
        CarViewDTO carViewDTO = new CarViewDTO(
            updatedCar.getId(), updatedCar.getBrand(), updatedCar.getModel(), updatedCar.getYear(), updatedCar.getColor(),
            updatedCar.getPrice(), updatedCar.getMileage(), updatedCar.getFuelType(),
            updatedCar.getTransmissionType(), updatedCar.getHorsepower(), updatedCar.getDoorCount(),
            updatedCar.getBodyType()
        );
        
        return ResponseEntity.ok(carViewDTO);
    }


    @DeleteMapping(value = "/cars/{car_id}/delete", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Car deleted")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "400", description = "Require input is not your car id")
    @ApiResponse(responseCode = "403", description = "Token Error: You are not the authorized person")
    @SecurityRequirement(name = "car-user-restApi")
    @Operation(summary = "Delete a car of a user")
    public ResponseEntity<Map<String,String>> deleteCar(Authentication authentication, @PathVariable Long car_id){
        String email = authentication.getName();
        Optional<User> optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Car> optionalCar = carService.findById(car_id);

            if (optionalCar.isPresent() && optionalCar.get().getUser().getId().equals(user.getId())) {
                carService.deleteById(car_id);
                
                Map<String, String> response = new HashMap<>();
                response.put("message", SuccessMessages.CAR_SUCCESSFULLY_DELETED.toString());
                return ResponseEntity.ok(response);
              
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message","Not match your car ids"));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message","User not found"));
    }
}
