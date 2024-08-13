package org.gunsugunaydin.SpringCarUser.repositories;

import java.util.List;

import org.gunsugunaydin.SpringCarUser.models.Car;
import org.gunsugunaydin.SpringCarUser.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findByUser(User user);
   
}
