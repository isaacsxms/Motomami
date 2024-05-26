package com.dam.tfg.MotoMammiApplicationIDS.model;

import jakarta.persistence.*;

@Entity
@Table(name = "MM_VEHICLE")
public class VehicleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "number_plate", unique = true)
    private String plateNumber;

    @Column(name = "type_vehicle")
    private String vehicleType;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @Column(name = "dni_customer")
    private String dni;

  /*   @ManyToOne
    @JoinColumn(name = "dni_customer", referencedColumnName = "dni", insertable = false, updatable = false)
    private CustomerDTO customer;
 */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    /* public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    } */

    public VehicleDTO() {}

    public VehicleDTO(int id, String plateNumber, String vehicleType, String brand, String model,
                      String color, String dni) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "VehicleDTO [id=" + id + ", plateNumber=" + plateNumber + ", vehicleType="
                + vehicleType + ", brand=" + brand + ", model=" + model + ", color=" + color + ", dni=" + dni + "]";
    }
}
