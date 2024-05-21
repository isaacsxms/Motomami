package com.dam.tfg.MotoMammiApplicationIDS.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_TRANSLATIONS")
public class TranslationsDTO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cod_prov")
    private String providerCode;

    @Column(name = "cod_int")
    private String internalCode;
    
    @Column(name = "cod_ext")
    private String externalCode;

    @Column(name = "date_ini")
    private Date initializeDate;

    @Column(name = "date_end")
    private Date endDate;

     @ManyToOne
    @JoinColumn(name = "cod_prov", referencedColumnName = "cod_prov", insertable = false, updatable = false)
    private ProviderDTO provider;

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

    public String getInternalCode() {
        return internalCode;
    }

    public void setInternalCode(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getExternalCode() {
        return externalCode;
    }

    public void setExternalCode(String externalCode) {
        this.externalCode = externalCode;
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

    public ProviderDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }

    public TranslationsDTO(){}
    
    public TranslationsDTO(int id, String providerCode, String internalCode, String externalCode, Date initializeDate,
            Date endDate, ProviderDTO provider) {
        this.id = id;
        this.providerCode = providerCode;
        this.internalCode = internalCode;
        this.externalCode = externalCode;
        this.initializeDate = initializeDate;
        this.endDate = endDate;
        this.provider = provider;
    }

    @Override
    public String toString() {
        return "TranslationsDTO [id=" + id + ", providerCode=" + providerCode + ", internalCode=" + internalCode
                + ", externalCode=" + externalCode + ", initializeDate=" + initializeDate + ", endDate=" + endDate
                + ", provider=" + provider + "]";
    }
}
