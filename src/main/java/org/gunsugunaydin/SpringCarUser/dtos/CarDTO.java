package org.gunsugunaydin.SpringCarUser.dtos;

import lombok.Getter;
import lombok.Setter;


//Car sınıfının id ve user olmayan hali--> Swaggger request body'de kullanıcı yeni araba eklerken id giremez ve kendini ekleyemez.
@Getter
@Setter
public class CarDTO {
    
    private String brand;

    private String model;

    private Integer year;

    private String color;

    private Double price;

    private Integer mileage;

    private String fuelType;

    private String transmissionType;

    private Integer horsepower;

    private Integer doorCount;

    private String bodyType;
}
