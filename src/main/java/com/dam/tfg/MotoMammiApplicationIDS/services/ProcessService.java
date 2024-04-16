package com.dam.tfg.MotoMammiApplicationIDS.services;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public interface ProcessService {
    public void readFileInfo(String p_source, String p_prov, Date p_Date); 
    // source being either Customer, Vehicles or Parts
}
