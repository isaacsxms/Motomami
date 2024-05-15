package com.dam.tfg.MotoMammiApplicationIDS.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.Constantes;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@RestController
public class Controller
{
    @Autowired
    ProcessService pService;

    @RequestMapping(value =("/readInfo/{resource}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    String callProcessReadInfo(@PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date//"20240423"
                               ){
        try{
            System.out.println("\nEsta tarea se lanza cada 15 segundos");
            //pService.readFileInfo(resource,codprov,date);
           

        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
        }
        System.out.println("El valor de resource es: " + resource);


        return "Buenos dias"; // here return the data in TO-DO.txt file
    }
}