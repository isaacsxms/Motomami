package com.dam.tfg.MotoMammiApplicationIDS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_CUSTOMER")
public class PartsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "cod_ext")
    private String externalCode;

    @Column(name = "cod_int")
    private String internalCode;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "date_notification")
    private Date dateNotification;

    @Column(name = "number_plate")
    private String numberPlate;

    @Column(name = "id_invoice")
    private int idInvoice;
    
    @Column(name = "dni_vehicle")
    private String dniVehicle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getDniVehicle() {
        return dniVehicle;
    }

    public void setDniVehicle(String dniVehicle) {
        this.dniVehicle = dniVehicle;
    }

    public PartsDTO(){}
    
    public PartsDTO(int id, String externalCode, String internalCode, String description, Date dateNotification,
    String numberPlate, int idInvoice, String dniVehicle) {
        this.id = id;
        this.externalCode = externalCode; // it's the vehicle plate bumber
        this.internalCode = internalCode;
        this.description = description;
        this.dateNotification = dateNotification;
        this.numberPlate = numberPlate;
        this.idInvoice = idInvoice;
        this.dniVehicle = dniVehicle;
    }

    @Override
    public String toString() {
        return "PartsDTO [id=" + id + ", externalCode=" + externalCode + ", internalCode=" + internalCode
                + ", description=" + description + ", dateNotification=" + dateNotification + ", numberPlate="
                + numberPlate + ", idInvoice=" + idInvoice + ", dniVehicle=" + dniVehicle + "]";
    }
}
