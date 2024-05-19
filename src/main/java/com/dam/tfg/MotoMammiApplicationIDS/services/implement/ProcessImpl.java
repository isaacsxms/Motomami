package com.dam.tfg.MotoMammiApplicationIDS.services.implement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationIDS.model.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.InterfaceDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;
import com.google.gson.Gson;

import jakarta.annotation.PostConstruct;

@Component
public class ProcessImpl implements ProcessService{
    @Value("${path.file.customer.name}")
    private String customersNameFile;
    @Value("${path.file.vehicles.name}")
    private String vehiclesNameFile;
    @Value("${path.file.parts.name}")
    private String partsNameFile;
    @Value("${path.resources.input}")
    private String resource;

    @PostConstruct
    public void logProperties() {
        System.out.println("Resource: " + resource);
        System.out.println("CustomersNameFile: " + customersNameFile);
        System.out.println("VehiclesNameFile: " + vehiclesNameFile);
        System.out.println("PartsNameFile: " + partsNameFile);
    }

    @Override
    public void readFileInfo(String p_source, String p_prov, String p_date) {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSession();

        // Check if active and date in between initialized and end provider date.
        List<ProviderDTO> activeSources = null;

        try {
        HibernateUtil.beginTransaction();
        activeSources = HibernateUtil.getCurrentSession().createQuery("FROM MM_PROVIDERS where active = 1 "+
        "and ifnull(:p_date,current_date()) BETWEEN initializeDate AND ifnull(endDate,'2099-01-31') "+
        "and providerCode = ifnull(:p_prov, providerCode)",ProviderDTO.class)
        .setParameter("p_prov", p_prov)
        .setParameter("p_date", p_date)
        .list();


        System.out.println("Active sources: " + activeSources.toString());

        if (activeSources.isEmpty()) {
            System.err.println("No active sources found.");
            return;
        }

        } catch(Exception e) {
            System.err.println("ERROR: Encountered on active users query-> " + e.getMessage());
        }

         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         if (p_date == null) {
            Date date = new Date();
            try{
                p_date = dateFormat.format(date);
            } catch (Exception e) {
                System.err.println("ERROR: Error formatting date -> " + e.getMessage());
            }
            System.out.println(p_date);
        }
        
        
        //String findVehiclesFile = null;
        //String findPartsFile = null;
        ArrayList<String> files = new ArrayList<>();
        
        ArrayList<CustomerDTO> customerList = new ArrayList<>();
        try{
        for (ProviderDTO activeSource : activeSources) {
            
            String providerCode = activeSource.getProviderCode();
            String findCustomersFile = resource + customersNameFile + providerCode + "_" + p_date + ".dat";
            System.out.println( resource + customersNameFile + providerCode + "_" + p_date + ".dat");
                //findVehiclesFile = resource + vehiclesNameFile + providerCode + "_" + formattedDate + ".dat";
                //findPartsFile = resource + partsNameFile + providerCode + "_" + formattedDate + ".dat";

                files.add(findCustomersFile);
                //files.add(findVehiclesFile);
                //files.add(findPartsFile);

                CustomerDTO newCustomerDTO = new CustomerDTO();
            for (String file : files) {
                System.out.println("File reading: " + file);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    try {
                        br.readLine(); // skip first line (column names)
                        while ((line = br.readLine()) != null) {
                            System.out.println("Reading file " + file + ": \n" + line + "\n");
                            // we read from file we have to convert to objects and insert into interface
                            String[] customer = line.split(";");
                            Date birthDate = null;
                            try {
                                birthDate = dateFormat.parse(customer[5]);
                                System.out.println("Birthdate parsed correctly: " + birthDate);
                            } catch (ParseException e) {
                                System.err.println("Error Parsing birth date: " + birthDate + "\n" + e.getCause());
                                //continue;
                            }
                            System.out.println(birthDate);
                            char gender = customer[11].charAt(0);
                            newCustomerDTO = new CustomerDTO(0, customer[0], customer[1], customer[2], customer[3], customer[4], birthDate, customer[6], customer[7], customer[8], customer[9], customer[10], gender);

                            customerList.add(newCustomerDTO);
                            System.out.println("Full List: " + customerList.toString());
                        }
                    } catch (IOException e) {
                        System.err.println("ERROR: Reading file... " + e.getMessage());
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("No se encontro el fichero! " + file);
                }
            }

            // TURN TO JSON
            //ArrayList<String> jsonCustomerList = new ArrayList<>();
            for (CustomerDTO customer : customerList) {
                System.out.println("Entered loop!");
                String jsonContent = new Gson().toJson(customer);

                // Check if the dni exists in InterfaceDTO
                InterfaceDTO existingRecord = HibernateUtil.getCurrentSession()
                .createQuery("FROM InterfaceDTO WHERE dni = :dni", InterfaceDTO.class)
                .setParameter("dni", customer.getDni())
                .uniqueResult();

                System.out.println("STEP 1");
                if (existingRecord != null) {
                    System.out.println("STEP 2");
                    // If dni and content JSON are the same, do nothing
                    if (existingRecord.getJsonContent().equals(jsonContent)) {
                        System.out.println("DNI and content JSON are the same. Skipping...");
                        continue;
                    } else {
                        // If dni is the same but JSON is different, update the interface
                        System.out.println("DNI exists but content JSON is different. Updating...");
                        existingRecord.setJsonContent(jsonContent);
                        HibernateUtil.getCurrentSession().update(existingRecord);
                    }
                } else {
                    // If dni doesn't exist, insert a new interface
                    System.out.println("DNI doesn't exist. Inserting new interface...");
                    InterfaceDTO newInterface = new InterfaceDTO();
                    newInterface.setJsonContent(jsonContent);
                    newInterface.seInternalCode(customer.getDni());
                    newInterface.setCreationDate(new Date());
                    newInterface.setLastUpdated(new Date());
                    newInterface.setProviderCode(p_prov);

                    // Makes no sense to add a provider object just add the prov_cod
                    //newInterface.setProviderCode(p_prov);
                    // Set other fields as needed
                    HibernateUtil.getCurrentSession().save(newInterface);
                }
            }

            // check if dni exists and if content json exists
            // if dni and cont json are the same we don't do anything
            // if dni is the same but json is different we insert an update in interface
            // if none of the above are the same we insert a new interface

            /* String checkDniQuery = "FROM InterfaceDTO WHERE dni = :dni";
            List<InterfaceDTO> existingRecords = HibernateUtil.getCurrentSession()
            .createQuery(checkDniQuery, InterfaceDTO.class)
            .setParameter("dni", newCustomerDTO.getDni())
            .list(); */
            
            // commit transactions
            HibernateUtil.commitTransaction();
        }} catch(Exception e) {
            
        }       
        HibernateUtil.closeSessionFactory();
    }
}
