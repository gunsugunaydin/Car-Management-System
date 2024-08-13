package org.gunsugunaydin.SpringCarUser.services;

import java.util.List;
import java.util.Optional;

import org.gunsugunaydin.SpringCarUser.dtos.CarDTO;
import org.gunsugunaydin.SpringCarUser.models.Car;
import org.gunsugunaydin.SpringCarUser.models.User;


public interface ICarService {

    public Optional<Car> findById(Long id);
    public List<Car> findByUser(User user);
    public Car save(Car car);
    public Car addNewCar(CarDTO carDto, User user);
    public Car updateCar(CarDTO carDto, Car car);
    public void deleteById(Long id);
}
