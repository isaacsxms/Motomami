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
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;
import com.google.gson.Gson;

import jakarta.annotation.PostConstruct;

@Component
public class ProcessImpl implements ProcessService {
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
        List<ProviderDTO> activeSources = getActiveSources(p_prov, p_date);
        if (activeSources == null || activeSources.isEmpty()) {
            System.err.println("No active sources found.");
            HibernateUtil.closeSessionFactory();
            return;
        }

        p_date = getCurrentDateIfNull(p_date);

        List<String> files = getFilePaths(activeSources, p_date);
        List<CustomerDTO> customerList = processCustomerFiles(files);

        processCustomerData(customerList, p_prov);

        HibernateUtil.commitTransaction();
        HibernateUtil.closeSessionFactory();
    }

    private List<ProviderDTO> getActiveSources(String p_prov, String p_date) {
        List<ProviderDTO> activeSources = null;
        try {
            HibernateUtil.beginTransaction();
            activeSources = HibernateUtil.getCurrentSession().createQuery(
                    "FROM MM_PROVIDERS where active = 1 " +
                            "and ifnull(:p_date,current_date()) BETWEEN initializeDate AND ifnull(endDate,'2099-01-31') "
                            +
                            "and providerCode = ifnull(:p_prov, providerCode)",
                    ProviderDTO.class)
                    .setParameter("p_prov", p_prov)
                    .setParameter("p_date", p_date)
                    .list();

        } catch (Exception e) {
            System.err.println("ERROR: Encountered on active users query-> " + e.getMessage());
        }
        return activeSources;
    }

    private String getCurrentDateIfNull(String p_date) {
        if (p_date == null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            p_date = dateFormat.format(new Date());
        }
        return p_date;
    }

    private List<String> getFilePaths(List<ProviderDTO> activeSources, String p_date) {
        List<String> files = new ArrayList<>();
        for (ProviderDTO activeSource : activeSources) {
            String providerCode = activeSource.getProviderCode();
            String findCustomersFile = resource + customersNameFile + providerCode + "_" + p_date + ".dat";

            System.out.println("File path: " + findCustomersFile);

            files.add(findCustomersFile);

            // findVehiclesFile = resource + vehiclesNameFile + providerCode + "_" +
            // formattedDate + ".dat";
            // findPartsFile = resource + partsNameFile + providerCode + "_" + formattedDate
            // + ".dat";
            // files.add(findVehiclesFile)
            // files.add(findPartsFile)
        }
        return files;
    }

    private List<CustomerDTO> processCustomerFiles(List<String> files) {
        List<CustomerDTO> customerList = new ArrayList<>();
        for (String file : files) {
            System.out.println("Reading file: " + file);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine(); // skip first line (column names)
                String line;
                while ((line = br.readLine()) != null) {
                    CustomerDTO customer = parseCustomer(line);
                    if (customer != null) {
                        customerList.add(customer);
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + file);
            } catch (IOException e) {
                System.err.println("ERROR: Reading file... " + e.getMessage());
            }
        }
        return customerList;
    }

    private CustomerDTO parseCustomer(String line) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] customerData = line.split(";");
        try {
            Date birthDate = dateFormat.parse(customerData[5]);
            char gender = customerData[11].charAt(0);
            return new CustomerDTO(0, customerData[0], customerData[1], customerData[2], customerData[3],
                    customerData[4], birthDate, customerData[6], customerData[7],
                    customerData[8], customerData[9], customerData[10], gender);
        } catch (ParseException e) {
            System.err.println("Error Parsing birth date: " + customerData[5] + " -> " + e.getMessage());
        }
        return null;
    }

    private void processCustomerData(List<CustomerDTO> customerList, String p_prov) {
        for (CustomerDTO customer : customerList) {
            String jsonContent = new Gson().toJson(customer);
            InterfaceDTO existingRecord = HibernateUtil.getCurrentSession()
                    .createQuery("FROM InterfaceDTO WHERE internalCode = :dni", InterfaceDTO.class)
                    .setParameter("dni", customer.getDni())
                    .uniqueResult();

            try {
                if (existingRecord != null) {
                    System.out.println("Interface exists!");
                    if (!existingRecord.getJsonContent().equals(jsonContent)) {
                        System.out.println("Entered! to update");
                        existingRecord.setJsonContent(jsonContent);
                        existingRecord.setStatusProcess('P');
                        existingRecord.setOperation("UPD");
                        HibernateUtil.getCurrentSession().update(existingRecord);
                    }
                    // IF IT HAS BOTH THE SAME DNI AND JSON CONTENT THEN IT WONT DO ANYTHING
                } else {
                    InterfaceDTO newInterface = new InterfaceDTO();
                    newInterface.setJsonContent(jsonContent);
                    newInterface.setInternalCode(customer.getDni());
                    newInterface.setCreationDate(new Date());
                    newInterface.setLastUpdated(new Date());
                    newInterface.setStatusProcess('P');
                    newInterface.setOperation("NEW");
                    newInterface.setProviderCode(p_prov);
                    HibernateUtil.getCurrentSession().save(newInterface);
                }
            } catch (Exception e) {

            }
        }
    }
}