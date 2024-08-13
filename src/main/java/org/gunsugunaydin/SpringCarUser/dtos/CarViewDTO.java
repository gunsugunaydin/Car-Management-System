package org.gunsugunaydin.SpringCarUser.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//Car sınıfının sadece user olmayan hali-->arabaları listeleyince car_id nin görünmesi delete ve update operasyonu için önemli.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarViewDTO {
    
    private Long id; 
    
    private String brand;

    private String model;

    private int year;

    private String color;

    private double price;

    private int mileage;

    private String fuelType;

    private String transmissionType;

    private int horsepower;

    private int doorCount;

    private String bodyType;
}
