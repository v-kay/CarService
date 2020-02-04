package com.heycar.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@ApiModel("Car")
@Data
@Entity
public class Car implements Comparable<Car> {

    @ApiModelProperty(notes = "The code of the dealer")
    @Id
    private String code;

    @ApiModelProperty(notes = "The make of the car")
    private String make;

    @ApiModelProperty(notes = "The model of the car")
    private String model;

    @ApiModelProperty(notes = "The power of the car")
    private Integer kw;

    @ApiModelProperty(notes = "The make year of the car")
    private Integer year;

    @ApiModelProperty(notes = "The color of the car")
    private String color;

    @ApiModelProperty(notes = "The price of the car")
    private BigDecimal price;


    @Override
    public int compareTo(Car car) {
        return this.code.compareTo(car.code);
    }
}
