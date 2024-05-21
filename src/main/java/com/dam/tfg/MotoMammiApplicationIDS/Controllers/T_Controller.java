package com.dam.tfg.MotoMammiApplicationIDS.Controllers;

import java.util.Collections;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.Constantes;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@RestController
public class T_Controller
{
    @Autowired
    ProcessService pService;

    @RequestMapping(value =("/readInfoFileIDS/{resource}/{codprov}/{date}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    HashMap<String, Integer> callProcessReadInfo(@PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date//"20240423"
                               ){
        try{
            System.out.println("Resource: " + resource + "\n" +
                               "Provider code: " + codprov + "\n" +
                               "Date: " + date);

        return pService.readFileInfo(resource, codprov, date);

        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
            return new HashMap<>(); // Return an empty map or handle error accordingly
        }
    }
    @RequestMapping(value =("/processInfoFileIDS/{resource}/{codprov}/{date}/{id_interface}"),
                    method = RequestMethod.GET,
                    produces = "application/json")
    HashMap<String, Integer> callProcessInfo(@PathVariable String resource,
                               @PathVariable String codprov,
                               @PathVariable String date,//"20240423"
                               @PathVariable Integer id_interface
                               ){
        try{
            System.out.println("Resource: " + resource + "\n" +
                               "Provider code: " + codprov + "\n" +
                               "Date: " + date + "\n" + 
                               "id_interface" + id_interface);

        return pService.integrateInfo(resource, codprov, date, id_interface);

        } catch (Exception e){
            System.err.println("heey pero me estoy poniendo peluche yo 😏😏");
            return new HashMap<>(); // Return an empty map or handle error accordingly
        }
    }
}