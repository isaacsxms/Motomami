package com.dam.tfg.MotoMammiApplicationIDS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_PARTS")
public class PartsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_notified")
    private Date dateNotified;

    @Column(name = "number_plate")
    private String numberPlate;
    
    @Column(name = "dni_customer")
    private String dniCustomer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateNotification() {
        return dateNotified;
    }

    public void setDateNotified(Date dateNotified) {
        this.dateNotified = dateNotified;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getDniCustomer() {
        return dniCustomer;
    }

    public void setDniCustomer(String dniCustomer) {
        this.dniCustomer = dniCustomer;
    }

    public PartsDTO(){}
    
    public PartsDTO(int id, String description, Date dateNotified,
    String numberPlate, String dniCustomer) {
        this.id = id;
        this.description = description;
        this.dateNotified = dateNotified;
        this.numberPlate = numberPlate;
        this.dniCustomer = dniCustomer;
    }

    @Override
    public String toString() {
        return "PartsDTO [id=" + id + ", description=" + description + ", dateNotified=" + dateNotified + ", numberPlate="
                + numberPlate + ", dniCustomer=" + dniCustomer + "]";
    }
}
