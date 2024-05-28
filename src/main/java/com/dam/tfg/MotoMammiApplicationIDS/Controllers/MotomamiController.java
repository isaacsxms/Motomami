package com.dam.tfg.MotoMammiApplicationIDS.Controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@RestController
public class MotomamiController {
    @Autowired
    ProcessService pService;

    @Value("${executed.by.controller}")
    private String exeController;
    
    @RequestMapping(value = ("/readInfoFileIDS/{resource}/{codprov}/{date}"), method = RequestMethod.GET, produces = "application/json")
    HashMap<String, Integer> callProcessReadInfo(@PathVariable String resource,
            @PathVariable String codprov,
            @PathVariable String date) {
        try {
            System.out.println("Resource: " + resource + "\n" +
                    "Provider code: " + codprov + "\n" +
                    "Date: " + date);

            return pService.readFileInfo(resource, codprov, date, exeController);

        } catch (Exception e) {
            System.err.println("ERROR: File processing into DTO's endpoint failed! \n" + e.getMessage());
            return new HashMap<>(); // Return an empty map or handle error accordingly
        }
    }

    @RequestMapping(value = ("/processInfoFileIDS/{resource}/{codprov}/{date}/{id_interface}"), method = RequestMethod.GET, produces = "application/json")
    HashMap<String, Integer> callProcessInfo(@PathVariable String resource,
            @PathVariable String codprov,
            @PathVariable String date,
            @PathVariable Integer id_interface) {
        try {
            System.out.println("Resource: " + resource + "\n" +
                    "Provider code: " + codprov + "\n" +
                    "Date: " + date + "\n" +
                    "id_interface" + id_interface);

            return pService.integrateInfo(resource, codprov, date, id_interface);

        } catch (Exception e) {
            System.err.println("ERROR: Resource integration endpoint failed! \n" + e.getMessage());
            return new HashMap<>(); // Return an empty map or handle error accordingly
        }
    }

    @RequestMapping(value = ("/genInvoiceFileIDS/{codprov}/{date}"), method = RequestMethod.GET, produces = "application/json")
    String callgenerateInvoice(@PathVariable String codprov,
            @PathVariable String date) {
        try {
            System.out.println("Provider code: " + codprov + "\n" +
                    "Date: " + date);

            return pService.generateCsv(codprov, date);
        } catch (Exception e) {
            System.err.println("ERROR: Invoice generation endpoint failed! \n" + e.getMessage());
            return "Internal error: Failed invoice generation!";
        }
    }
}