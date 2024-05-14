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

    @Override
    public void readFileInfo(String p_source, String p_prov, String p_date) {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSession();

        
        // Check if active and date in between initialized and end provider date.
        List<ProviderDTO> activeSources = null;

        try {
            
        activeSources = HibernateUtil.getCurrentSession().createQuery("FROM MM_PROVIDERS where active = 1 "+
        "and ifnull(:p_date,current_date()) BETWEEN initializeDate AND ifnull(endDate,'2099-01-31') "+
        "and providerCode = ifnull(:p_prov, providerCode)",ProviderDTO.class)
        .setParameter("p_prov", p_prov)
        .setParameter("p_date", p_date)
        .list();


        System.out.println("Active sources: " + activeSources.toString());
        } catch(Exception e) {
            System.err.println("ERROR: Encountered on active users query-> " + e.getMessage());
        }
        

         String providerCode = null;

         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");;
         if (p_date == null) {
            Date date = new Date();
            try{
                p_date = dateFormat.format(date);
            } catch (Exception e) {
                System.err.println("ERROR: Error formatting date -> " + e.getMessage());
            }
            System.out.println(p_date);
        }
        
        String findCustomersFile = null;
        //String findVehiclesFile = null;
        //String findPartsFile = null;
        ArrayList<String> files = new ArrayList<>();
        
        ArrayList<CustomerDTO> customerList = new ArrayList<>();
        for (ProviderDTO activeSource : activeSources) {
            providerCode = activeSource.getProviderCode();
                findCustomersFile = resource + customersNameFile + providerCode + "_" + p_date + ".dat";
                System.out.println("resource"+ resource + "customersNameFile" +customersNameFile );
                //findVehiclesFile = resource + vehiclesNameFile + providerCode + "_" + formattedDate + ".dat";
                //findPartsFile = resource + partsNameFile + providerCode + "_" + formattedDate + ".dat";

                files.add(findCustomersFile);
                //files.add(findVehiclesFile);
                //files.add(findPartsFile);

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
                            }
                            System.out.println(birthDate);
                            char gender = customer[11].charAt(0);
                            CustomerDTO newCustomerDTO = new CustomerDTO(0, customer[0], customer[1], customer[2], customer[3], customer[4], birthDate, customer[6], customer[7], customer[8], customer[9], customer[10], gender);

                            customerList.add(newCustomerDTO);

                            // TURN TO JSON
                            String jsonCustomer = new Gson().toJson(newCustomerDTO);

                            System.out.println("Json customer: " + jsonCustomer);

                            // check if dni exists and if content json exists
                            // if dni and cont json are the same we don't do anything
                            // if dni is the same but json is different we insert an update in interface
                            // if none of the above are the same we insert a new interface
                            String checkDniQuery = "FROM InterfaceDTO WHERE dni = :dni";
                            List<InterfaceDTO> existingRecords = HibernateUtil.getCurrentSession()
                            .createQuery(checkDniQuery, InterfaceDTO.class)
                            .setParameter("dni", newCustomerDTO.getDni())
                            .list();

                            System.out.println("Existing users" + existingRecords);

                            boolean recordExists = false;
                            for (InterfaceDTO record : existingRecords) {
                                /* if (record.getJsonContent().equals(newCustomerDTO.toJson())) {
                                    recordExists = true;
                                    break;
                                } */
                            }
                        }
                        System.out.println("Full List: " + customerList.toString());
                    } catch (IOException e) {
                        System.err.println("ERROR: Reading file... " + e.getMessage());
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("No se encontro el fichero! " + file);
                }
            }
        }        
        HibernateUtil.closeSessionFactory();
    }
}
