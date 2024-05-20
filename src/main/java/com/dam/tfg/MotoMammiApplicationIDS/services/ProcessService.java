package com.dam.tfg.MotoMammiApplicationIDS.services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public interface ProcessService {
    public HashMap<String, Integer> readFileInfo(String p_source, String p_prov, String p_Date); 
    // source being either Customer, Vehicles or Parts
}
