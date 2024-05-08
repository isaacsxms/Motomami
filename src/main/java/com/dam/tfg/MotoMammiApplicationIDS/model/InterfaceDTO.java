package com.dam.tfg.MotoMammiApplicationIDS.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MM_INTERFACE")
public class InterfaceDTO {
    
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "cod_ext")
    private String externalCode;

    @Column(name = "cod_prov")
    private String providerCode;

    @Column(name = "cont_json")
    private String jsonContent;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "cod_error")
    private String errorCode;

    @Column(name = "error_message")
    private String errorMessage;
    
    @Column(name = "status_process") // 'N' or 'E' or'P'
    private char statusProcess;
    
    @Column(name = "operation") // 'NEW' or 'UPD'
    private char operation;

    @Column(name = "resources") // 'CUS' or 'VEH' or 'PRT'
    private char resources;

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

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getJsonContent() {
        return jsonContent;
    }

    public void setJsonContent(String jsonContent) {
        this.jsonContent = jsonContent;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public char getStatusProcess() {
        return statusProcess;
    }

    public void setStatusProcess(char statusProcess) {
        this.statusProcess = statusProcess;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public char getResources() {
        return resources;
    }

    public void setResources(char resources) {
        this.resources = resources;
    }

    public InterfaceDTO(){};

    public InterfaceDTO(int id, String externalCode, String providerCode, String jsonContent, Date creationDate,
            Date lastUpdated, String createdBy, String updatedBy, String errorCode, String errorMessage,
            char statusProcess, char operation, char resources) {
        this.id = id;
        this.externalCode = externalCode;
        this.providerCode = providerCode;
        this.jsonContent = jsonContent;
        this.creationDate = creationDate;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.statusProcess = statusProcess;
        this.operation = operation;
        this.resources = resources;
    };
}
