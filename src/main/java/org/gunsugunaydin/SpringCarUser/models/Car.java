package org.gunsugunaydin.SpringCarUser.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "car_id")
    private Long id;

    @NotBlank(message = "Brand must be entered.")
    @Column(name = "brand")
    private String brand;

    @NotBlank(message = "Model must be entered.")
    @Column(name = "model")
    private String model;
    
    @Min(value = 1886, message = "Year must be greater than or equal to 1886.") // İlk otomobilin yapıldığı yıl
    @Max(value = 9999, message = "Year must be less than or equal to 9999.") // Yıl sınırı
    @Column(name = "car_year")
    private int year;

    @NotBlank(message = "Color must be entered.")
    @Column(name = "color")
    private String color;

    @DecimalMin(value = "0.0", message = "Price must be greater than or equal to 0.0")
    @Column(name = "price")
    private double price;

    @Min(value = 0, message = "Mileage must be greater than or equal to 0")
    @Column(name = "mileage")
    private int mileage;

    @NotBlank(message = "Fuel type must be entered(e.g. benzin, dizel, elektrik).")
    @Column(name = "fuel_type")
    private String fuelType;

    @NotBlank(message = "Transmission type must be entered.(e.g. manuel, otomatik).")
    @Column(name = "transmission_type")
    private String transmissionType;

    @Min(value = 0, message = "Horsepower must be greater than or equal to 0")
    @Column(name = "horsepower")
    private int horsepower;

    @Min(value = 1, message = "Door count must be greater than or equal to 1")
    @Max(value = 10, message = "Door count must be less than or equal to 10")
    @Column(name = "door_count")
    private int doorCount;

    @NotBlank(message = "Body type must be entered(e.g. sedan,SUV,hatchback).")
    @Column(name = "body_type")
    private String bodyType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}

