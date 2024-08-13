package org.gunsugunaydin.SpringCarUser.services;

import java.util.List;
import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.dtos.CarDTO;
import org.gunsugunaydin.SpringCarUser.models.Car;
import org.gunsugunaydin.SpringCarUser.models.User;
import org.gunsugunaydin.SpringCarUser.repositories.CarRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


@Service
public class CarServiceImpl implements ICarService {
    
    
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> findByUser(User user) {
        return carRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Car save(Car car){
        return carRepository.save(car);
    }

    @Override
    @Transactional
    public Car addNewCar(CarDTO carDto, User user){
        Car car = new Car();
        car.setBrand(carDto.getBrand());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setColor(carDto.getColor());
        car.setPrice(carDto.getPrice());
        car.setMileage(carDto.getMileage());
        car.setFuelType(carDto.getFuelType());
        car.setTransmissionType(carDto.getTransmissionType());
        car.setHorsepower(carDto.getHorsepower());
        car.setDoorCount(carDto.getDoorCount());
        car.setBodyType(carDto.getBodyType());
        car.setUser(user);
        return save(car);
    }

    @Override
    @Transactional
    public Car updateCar(CarDTO carDto, Car car) {
        if (carDto.getBrand() != null && !carDto.getBrand().equals(car.getBrand())) {
            car.setBrand(carDto.getBrand());
        }
        if (carDto.getModel() != null && !carDto.getModel().equals(car.getModel())) {
            car.setModel(carDto.getModel());
        }
        if (carDto.getYear() != null && !carDto.getYear().equals(car.getYear())) {
            car.setYear(carDto.getYear());
        }
        if (carDto.getColor() != null && !carDto.getColor().equals(car.getColor())) {
            car.setColor(carDto.getColor());
        }
        if (carDto.getPrice() != null && !carDto.getPrice().equals(car.getPrice())) {
            car.setPrice(carDto.getPrice());
        }
        if (carDto.getMileage() != null && !carDto.getMileage().equals(car.getMileage())) {
            car.setMileage(carDto.getMileage());
        }
        if (carDto.getFuelType() != null && !carDto.getFuelType().equals(car.getFuelType())) {
            car.setFuelType(carDto.getFuelType());
        }
        if (carDto.getTransmissionType() != null && !carDto.getTransmissionType().equals(car.getTransmissionType())) {
            car.setTransmissionType(carDto.getTransmissionType());
        }
        if (carDto.getHorsepower() != null && !carDto.getHorsepower().equals(car.getHorsepower())) {
            car.setHorsepower(carDto.getHorsepower());
        }
        if (carDto.getDoorCount() != null && !carDto.getDoorCount().equals(car.getDoorCount())) {
            car.setDoorCount(carDto.getDoorCount());
        }
        if (carDto.getBodyType() != null && !carDto.getBodyType().equals(car.getBodyType())) {
            car.setBodyType(carDto.getBodyType());
        }
        return save(car);  
    } 
     
    @Override
    @Transactional
    public void deleteById(Long id){
        carRepository.deleteById(id);
    }
}
