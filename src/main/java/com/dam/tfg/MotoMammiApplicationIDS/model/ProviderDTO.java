package com.dam.tfg.MotoMammiApplicationIDS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "MM_PROVIDERS")
@Table
public class ProviderDTO {
    
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "cod_prov")
    private String providerCode;

    @Column(name = "prov_name")
    private String providerName;

    @Column(name = "date_ini")
    private Date initializeDate = new Date();

    @Column(name = "date_end")
    private Date endDate = new Date();

    @Column(name = "swiact")
    private boolean active;

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

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public Date getInitializeDate() {
        return initializeDate;
    }

    public void setInitializeDate(Date initializeDate) {
        this.initializeDate = initializeDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ProviderDTO() {};
    public ProviderDTO(int id, String providerCode, String providerName, Date initializeDate, Date endDate,
            boolean active) {
        this.id = id;
        this.providerCode = providerCode;
        this.providerName = providerName;
        this.initializeDate = initializeDate;
        this.endDate = endDate;
        this.active = active;
    };
    
    @Override
    public String toString() {
        return "ProviderDTO [id=" + id + ", providerCode=" + providerCode + ", providerName=" + providerName
                + ", initializeDate=" + initializeDate + ", endDate=" + endDate + ", active=" + active + "]";
    }
}
