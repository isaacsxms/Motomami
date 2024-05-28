package com.dam.tfg.MotoMammiApplicationIDS.services;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public interface ProcessService {
    public HashMap<String, Integer> readFileInfo(String p_source, String p_prov, String p_Date, String updateCreateBy); 
    public HashMap<String, Integer> integrateInfo(String p_source, String p_prov, String p_Date, Integer id_interface); 
    public String generateCsv(String p_prov, String p_date);
}
