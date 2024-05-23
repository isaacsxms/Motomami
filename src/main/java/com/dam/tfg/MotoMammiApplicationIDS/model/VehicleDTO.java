package com.dam.tfg.MotoMammiApplicationIDS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_VEHICLE")
public class VehicleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ext_id")
    private String ext_id;

    @Column(name = "number_plate")
    private String plateNumber;

    @Column(name = "type_vehicle")
    private String vehicleType;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;
    
    @Column(name = "color")
    private String color;

    @Column(name = "serialNumber")
    private String serialNumber;
    
    @Column(name = "dni")
    private String dni;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExt_id() {
        return ext_id;
    }

    public void setExt_id(String ext_id) {
        this.ext_id = ext_id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public VehicleDTO(){}
    
    public VehicleDTO(int id, String ext_id, String plateNumber, String vehicleType, String brand, String model,
    String color, String serialNumber, String dni) {
        this.id = id;
        this.ext_id = ext_id;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.serialNumber = serialNumber;
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "VehicleDTO [id=" + id + ", ext_id=" + ext_id + ", plateNumber=" + plateNumber + ", vehicleType="
                + vehicleType + ", brand=" + brand + ", model=" + model + ", color=" + color + ", serialNumber="
                + serialNumber + ", dni=" + dni + "]";
    }
}
