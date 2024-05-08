package com.dam.tfg.MotoMammiApplicationIDS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_CUSTOMER")
public class CustomerDTO {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "name")
    private String name;

    @Column(name = "first_surname")
    private String firstSurname;

    @Column(name = "second_surname")
    private String secondSurname;

    @Column(name = "email")
    private String email;
    
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "street_type")
    private String streetType;
    
    @Column(name = "city_customer")
    private String customerCity;
    
    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "gender")
    private char gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstSurname() {
        return firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurname = secondSurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public CustomerDTO(){};
    
    public CustomerDTO(int id, String dni, String name, String firstSurname, String secondSurname, String email, Date birthDate,
            String postalCode, String streetType, String customerCity, String streetNumber, String telephone,
            char gender) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.email = email;
        this.birthDate = birthDate;
        this.postalCode = postalCode;
        this.streetType = streetType;
        this.customerCity = customerCity;
        this.streetNumber = streetNumber;
        this.telephone = telephone;
        this.gender = gender;
    }  

    @Override
    public String toString() {
        return "CustomerDTO [id=" + id + ", name=" + name + ", firstSurname=" + firstSurname + ", secondSurname="
                + secondSurname + ", email=" + email + ", birthDate=" + birthDate + ", postalCode=" + postalCode
                + ", streetType=" + streetType + ", customerCity=" + customerCity + ", streetNumber=" + streetNumber
                + ", telephone=" + telephone + ", gender=" + gender + "]";
    }
}
