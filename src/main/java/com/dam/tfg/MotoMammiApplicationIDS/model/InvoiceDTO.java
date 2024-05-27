package com.dam.tfg.MotoMammiApplicationIDS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_INVOICES")
public class InvoiceDTO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cod_prov")
    private String providerCode;

    @Column(name = "dni_customer")
    private String dni;

    @Column(name = "date_emitted")
    private Date dateEmitted;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_cif")
    private String companyCif;

    @Column(name = "company_address")
    private String companyAddress;
    
    @Column(name = "price")
    private long price;

    @Column(name = "iva")
    private int iva;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getDateEmitted() {
        return dateEmitted;
    }

    public void setDateEmitted(Date dateEmitted) {
        this.dateEmitted = dateEmitted;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCif() {
        return companyCif;
    }

    public void setCompanyCif(String companyCif) {
        this.companyCif = companyCif;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public InvoiceDTO(){}

    public InvoiceDTO(int id, String providerCode, String dni, Date dateEmitted, String companyName,
    String companyCif, String companyAddress, long price, int iva) {
        this.id = id;
        this.providerCode = providerCode;
        this.dni = dni;
        this.dateEmitted = dateEmitted;
        this.companyName = companyName;
        this.companyCif = companyCif;
        this.companyAddress = companyAddress;
        this.price = price;
        this.iva = iva;
    }

    @Override
    public String toString() {
    return "InvoiceDTO [id=" + id + ", providerCode=" + providerCode + ", dni=" + dni + ", dateEmitted="
            + dateEmitted + ", companyName=" + companyName + ", companyCif=" + companyCif + ", companyAddress="
            + companyAddress + ", price=" + price + ", iva=" + iva + "]";
    }

}
