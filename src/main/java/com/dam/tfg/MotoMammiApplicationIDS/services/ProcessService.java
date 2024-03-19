package com.dam.tfg.MotoMammiApplicationIDS.services;

import org.springframework.stereotype.Service;

@Service
public interface ProcessService {

    public void readFileInfo(String source); // source being either Customer, Vehicles or Parts
}
