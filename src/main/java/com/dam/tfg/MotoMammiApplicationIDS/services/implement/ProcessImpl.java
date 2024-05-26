package com.dam.tfg.MotoMammiApplicationIDS.services.implement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.Constantes;
import com.dam.tfg.MotoMammiApplicationIDS.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationIDS.model.CustomerDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.InterfaceDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.PartsDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.TranslationDTO;
import com.dam.tfg.MotoMammiApplicationIDS.model.VehicleDTO;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;
import com.google.gson.Gson;

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
    @Value("${system.username}")
    private String systemUsername;

    private static int numLines = 0;
    private static int numInserted = 0;
    private static int numUpdated = 0;
    private static int numErrors = 0;

    @Override
    public HashMap<String, Integer> readFileInfo(String p_source, String p_prov, String p_date) {
        HashMap<String, Integer> statistics = new HashMap<>();
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSession();

        // Check if active and date in between initialized and end provider date.
        List<ProviderDTO> activeSources = getActiveSources(p_prov, p_date);
        if (activeSources == null || activeSources.isEmpty()) {
            System.err.println("No active sources found.");
            HibernateUtil.closeSessionFactory();
            return statistics;
        }

        p_date = getCurrentDateIfNull(p_date);

        List<String> files = getFilePaths(activeSources, p_date);

        switch (p_source) {
            case Constantes.C_CUSTOMERS:
                System.out.println("Enters customer switch!");
                List<CustomerDTO> customerList = processCustomerFiles(files.get(0), statistics);
                processCustomerData(customerList, p_prov, p_source, statistics);
                break;
            case Constantes.C_VEHICLES:
                List<VehicleDTO> vehicleList = processVehicleFiles(files.get(1), statistics);
                processVehicleData(vehicleList, p_prov, p_source, statistics);
                break;
            case Constantes.C_PARTS:
                List<PartsDTO> partsList = processPartsFiles(files.get(2), statistics);
                processPartsData(partsList, p_prov, p_source, statistics);
                System.out.println(partsList.toString());
            default:
                break;
        }

        HibernateUtil.commitTransaction();
        HibernateUtil.closeSessionFactory();

        statistics.put("numLines", numLines);
        statistics.put("numInserted", numInserted);
        statistics.put("numUpdated", numUpdated);
        statistics.put("numErrors", numErrors);

        return statistics;
    }

    private List<ProviderDTO> getActiveSources(String p_prov, String p_date) {
        List<ProviderDTO> activeSources = null;
        try {
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
            String findVehiclesFile = resource + vehiclesNameFile + providerCode + "_" + p_date + ".dat";
            String findPartsFile = resource + partsNameFile + providerCode + "_" + p_date + ".dat";

            files.add(findCustomersFile);
            files.add(findVehiclesFile);
            files.add(findPartsFile);
        }
        return files;
    }

    private List<CustomerDTO> processCustomerFiles(String file, HashMap<String, Integer> statistics) {
        List<CustomerDTO> customerList = new ArrayList<>();
            System.out.println("Reading file: " + file);
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine(); // skip first line (column names)
                String line;
                while ((line = br.readLine()) != null) {
                    numLines++;
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
        statistics.put("numLines", numLines);
        return customerList;
    }

    private List<VehicleDTO> processVehicleFiles(String vehicleFile, HashMap<String, Integer> statistics) {
        List<VehicleDTO> vehicleList = new ArrayList<>();
    
        System.out.println("Reading file: " + vehicleFile);
    
        try (BufferedReader br = new BufferedReader(new FileReader(vehicleFile))) {
            br.readLine(); // skip first line (column names)
            String line;
            while ((line = br.readLine()) != null) {
                numLines++;
                VehicleDTO vehicle = parseVehicle(line);
                if (vehicle != null) {
                    vehicleList.add(vehicle);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File -> " + vehicleFile +  " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        statistics.put("numLines", numLines);
        return vehicleList;
    }

    private List<PartsDTO> processPartsFiles(String partsFile, HashMap<String, Integer> statistics) {
        List<PartsDTO> partsList = new ArrayList<>();
    
        System.out.println("Reading file: " + partsFile);
    
        try (BufferedReader br = new BufferedReader(new FileReader(partsFile))) {
            br.readLine(); // skip first line (column names)
            String line;
            while ((line = br.readLine()) != null) {
                numLines++;
                PartsDTO part = parseParts(line);
                if (part != null) {
                    System.out.println("ADDING: " + part.toString());
                    partsList.add(part);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: File -> " + partsFile +  " not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        statistics.put("numLines", numLines);
        return partsList;
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

    private VehicleDTO parseVehicle(String line) {
        try {
            String[] vehicleData = line.split(";");

            return new VehicleDTO(0, vehicleData[0], vehicleData[1], vehicleData[2], vehicleData[3], vehicleData[4], vehicleData[5]);
        } catch(Exception e) {
            System.err.println("ERROR: Parsing vehicle " + e.getMessage());
        }       
        return null;
    }

    private PartsDTO parseParts(String line) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String[] partsData = line.split(";");
            Date dateNotified = dateFormat.parse(partsData[1]);

            return new PartsDTO(0, partsData[0], dateNotified, partsData[2], Integer.parseInt(partsData[3]), partsData[4]);
        } catch(Exception e) {
            System.err.println("ERROR: Parsing Parts " + e.getMessage());
        }       
        return null;
    }

    private void processVehicleData(List<VehicleDTO> vehicleList, String p_prov, String p_source, HashMap<String, Integer> statistics) {
        for (VehicleDTO vehicle : vehicleList) {
            String jsonContent = new Gson().toJson(vehicle);

            InterfaceDTO existingRecord = null;
            try {
                existingRecord = HibernateUtil.getCurrentSession()
                .createQuery("FROM InterfaceDTO WHERE internalCode = :dni", InterfaceDTO.class)
                .setParameter("dni", vehicle.getDni())
                .uniqueResult();
            } catch(Exception e) {
                e.printStackTrace();
            }
                    try {
                        if (existingRecord != null && existingRecord.getResources().equals(p_source)) {
                            System.out.println("Interface exists!");
                            if (!existingRecord.getJsonContent().equals(jsonContent) ) {
                                System.out.println("Entered! to update");
                                existingRecord.setJsonContent(jsonContent);
                                existingRecord.setUpdatedBy(systemUsername);
                                existingRecord.setOperation(Constantes.OPR_UPDATE);
                                HibernateUtil.getCurrentSession().update(existingRecord);
                                numUpdated++;
                            }
                            // IF IT HAS BOTH THE SAME DNI AND JSON CONTENT THEN IT WONT DO ANYTHING
                        } else {
                            InterfaceDTO newInterface = new InterfaceDTO();
                            newInterface.setExternalCode(generateExternalCode(p_prov, vehicle.getDni()));
                            newInterface.setJsonContent(jsonContent);
                            newInterface.setInternalCode(vehicle.getDni());
                            newInterface.setCreationDate(new Date());
                            newInterface.setLastUpdated(new Date());
                            newInterface.setStatusProcess('N'); // STATUS N TILL IT IS SUCCESFULLY INSERTED INTO CUS, VEH or PRT
                            newInterface.setOperation(Constantes.OPR_NEW);
                            newInterface.setCreatedBy(systemUsername);
                            newInterface.setProviderCode(p_prov);
                            newInterface.setResources(p_source);
                            HibernateUtil.getCurrentSession().save(newInterface);
                            numInserted++;
                        }
                    } catch (Exception e) {
                        System.err.println("ERROR INSERTING INTO INTERFACE: " + e.getMessage());
                    }
                }
                
                statistics.put("numInserted", numInserted);
                statistics.put("numUpdated", numUpdated);
                statistics.put("numErrors", numErrors);
    }

    private void processCustomerData(List<CustomerDTO> customerList, String p_prov, String p_source,
            HashMap<String, Integer> statistics) {
        for (CustomerDTO customer : customerList) {
            String jsonContent = new Gson().toJson(customer);
            System.out.println(jsonContent);

            InterfaceDTO existingRecord = null;
            try {
                existingRecord = HibernateUtil.getCurrentSession()
                       .createQuery("FROM InterfaceDTO WHERE internalCode = :dni AND resources = :p_source", InterfaceDTO.class)
                       .setParameter("dni", customer.getDni())
                       .setParameter("p_source", p_source)
                       .uniqueResult();
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            try {
                if (existingRecord != null && p_source.equals(Constantes.C_CUSTOMERS)) {
                    if (!existingRecord.getJsonContent().equals(jsonContent)) {
                        System.out.println("Entered! to update: " + jsonContent);
                        existingRecord.setJsonContent(jsonContent);
                        existingRecord.setUpdatedBy(systemUsername);
                        existingRecord.setOperation(Constantes.OPR_UPDATE);
                        HibernateUtil.getCurrentSession().update(existingRecord);
                        numUpdated++;
                    } else {
                        System.out.println("No changes made, not inserting or updating customer!");
                        // IF IT HAS BOTH THE SAME DNI AND JSON CONTENT THEN IT WONT DO ANYTHING
                    }
                } else {
                    System.out.println("Will insert new Customer Interfaces!");
                    InterfaceDTO newInterface = new InterfaceDTO();
                    newInterface.setExternalCode(generateExternalCode(p_prov, customer.getDni()));
                    newInterface.setJsonContent(jsonContent);
                    newInterface.setInternalCode(customer.getDni());
                    newInterface.setCreationDate(new Date());
                    newInterface.setLastUpdated(new Date());
                    newInterface.setStatusProcess('N'); // STATUS N TILL IT IS SUCCESFULLY INSERTED INTO CUS, VEH or PRT
                    newInterface.setOperation(Constantes.OPR_NEW);
                    newInterface.setCreatedBy(systemUsername);
                    newInterface.setProviderCode(p_prov);
                    newInterface.setResources(p_source);
                    HibernateUtil.getCurrentSession().save(newInterface);
                    numInserted++;
                }
            } catch (Exception e) {
                System.err.println("ERROR INSERTING INTO INTERFACE: " + e.getMessage());
            }
        }
        statistics.put("numInserted", numInserted);
        statistics.put("numUpdated", numUpdated);
        statistics.put("numErrors", numErrors);
    }

    private void processPartsData(List<PartsDTO> partsList, String p_prov, String p_source, HashMap<String, Integer> statistics) {
        for (PartsDTO part : partsList) {
            String jsonContent = new Gson().toJson(part);

            System.out.println("JSON: " + jsonContent);
            System.out.println("CUSTOMER DNI: " + part.getDniCustomer());
            
            InterfaceDTO existingRecord = null;
            try {

            
                existingRecord = HibernateUtil.getCurrentSession()
                .createQuery("FROM InterfaceDTO WHERE internalCode = :dni AND resources = :p_source", InterfaceDTO.class)
                .setParameter("dni", part.getDniCustomer())
                .setParameter("p_source", p_source)
                .uniqueResult();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                    try {
                        System.out.println("DOES IT ENTER HERE?");

                        if (existingRecord != null && existingRecord.getResources().equals(p_source)) {
                            System.out.println("Interface exists!");
                            if (!existingRecord.getJsonContent().equals(jsonContent) ) {
                                System.out.println("Entered! to update");
                                existingRecord.setJsonContent(jsonContent);
                                existingRecord.setUpdatedBy(systemUsername);
                                existingRecord.setOperation(Constantes.OPR_UPDATE);
                                HibernateUtil.getCurrentSession().update(existingRecord);
                                numUpdated++;
                            }
                            // IF IT HAS BOTH THE SAME DNI AND JSON CONTENT THEN IT WONT DO ANYTHING
                        } else {
                            System.out.println("New part insertions: ");
                            InterfaceDTO newInterface = new InterfaceDTO();
                            newInterface.setExternalCode(generateExternalCode(p_prov, part.getDniCustomer()));
                            newInterface.setJsonContent(jsonContent);
                            newInterface.setInternalCode(part.getDniCustomer());
                            newInterface.setCreationDate(new Date());
                            newInterface.setLastUpdated(new Date());
                            newInterface.setStatusProcess('N'); // STATUS N TILL IT IS SUCCESFULLY INSERTED INTO CUS, VEH or PRT
                            newInterface.setOperation(Constantes.OPR_NEW);
                            newInterface.setCreatedBy(systemUsername);
                            newInterface.setProviderCode(p_prov);
                            newInterface.setResources(p_source);
                            HibernateUtil.getCurrentSession().save(newInterface);
                            numInserted++;
                        }
                    } catch (Exception e) {
                        System.err.println("ERROR INSERTING INTO INTERFACE: " + e.getMessage());
                    }
                }
                
                statistics.put("numInserted", numInserted);
                statistics.put("numUpdated", numUpdated);
                statistics.put("numErrors", numErrors);
    }

    private String generateExternalCode(String p_prov, String dni) {
        switch (p_prov) {
            case "CAX":
                return "C-" + dni + "-X";
            case "SAN":
                return "S-" + dni + "-N";
            case "ING":
                return "I-" + dni + "-G";
            default:
                return "";
        }
    }

    public HashMap<String, Integer> integrateInfo(String p_source, String p_prov, String p_date, Integer id_interface) {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSession();
    
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        p_date = getCurrentDateIfNull(p_date);
    
        List<InterfaceDTO> unprocessedInterfaceList = HibernateUtil.getCurrentSession()
                .createQuery("FROM InterfaceDTO WHERE statusProcess = 'N' AND providerCode = ifnull(:p_prov, providerCode) AND resources = :p_source", InterfaceDTO.class)
                .setParameter("p_prov", p_prov)
                .setParameter("p_source", p_source)
                .list();
    
        for (InterfaceDTO obj : unprocessedInterfaceList) {
            try {
                System.out.println("Processing InterfaceDTO with resources: " + obj.getResources());
                switch (obj.getResources()) {
                    case Constantes.C_CUSTOMERS:
                    integrateCustomer(obj, session, p_date);
                        break;
                    case Constantes.C_VEHICLES:
                    integrateVehicle(obj, session, p_date);
                        break;
                    case Constantes.C_PARTS:
                    integrateParts(obj, session, p_date);
                        break;
                    default:
                        System.out.println("Unknown resource type");
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error processing InterfaceDTO: " + e.getMessage());
                e.printStackTrace();
                obj.setStatusProcess('E');
                session.update(obj);
                transaction.rollback();
                session.clear();
                break; // Break on error to prevent partial commits
            }
        }
    
        transaction.commit();
        HibernateUtil.closeSessionFactory();
    
        return null;
    }
    
    // CUSTOMER
    private void integrateCustomer(InterfaceDTO obj, Session session, String p_date) {
        System.out.println("Enters customer integrate case");
        CustomerDTO newCustomer = new Gson().fromJson(obj.getJsonContent(), CustomerDTO.class);
        String traduccion_tipo_via = getTranslation(obj.getProviderCode(), newCustomer.getStreetType(), p_date);
        newCustomer.setStreetType(traduccion_tipo_via);
        session.save(newCustomer);
        obj.setStatusProcess('P');
        session.update(obj);
    }

    // VEHICLE
    private void integrateVehicle(InterfaceDTO obj, Session session, String p_date) {
        System.out.println("Enters vehicle integrate case");
        VehicleDTO newVehicle = new Gson().fromJson(obj.getJsonContent(), VehicleDTO.class);
        String traduccion_color = getTranslation(obj.getProviderCode(), newVehicle.getColor(), p_date);
        if (traduccion_color == null) {
            traduccion_color = newVehicle.getColor(); // Fallback to the original color if no translation is found
        }
        System.out.println(newVehicle.toString());
        newVehicle.setColor(traduccion_color);
    
        try {
            // Check if the customer exists
            CustomerDTO existingCustomer = session.createQuery("FROM CustomerDTO WHERE dni = :dni", CustomerDTO.class)
            .setParameter("dni", newVehicle.getDni())
            .uniqueResult();

            if (existingCustomer != null) {
                System.out.println("Customer exists!");
                session.save(newVehicle);
                obj.setStatusProcess('P');  
            } else {
                System.err.println("Customer with DNI " + newVehicle.getDni() + " does not exist. Skipping vehicle insertion.");
                obj.setErrorMessage("Customer with DNI " + newVehicle.getDni() + " does not exist. Skipping vehicle insertion.");
                obj.setStatusProcess('E');
            }
        } catch (Exception e) {
            System.err.println("Error saving VehicleDTO: " + e.getMessage());
            obj.setErrorMessage("Error saving VehicleDTO: " + e.getMessage());
            obj.setStatusProcess('E');
        }
        session.update(obj);
    }

    // PARTS
    private void integrateParts(InterfaceDTO obj, Session session, String p_date) {
        System.out.println("Enters parts integrate case");
        PartsDTO newPart = new Gson().fromJson(obj.getJsonContent(), PartsDTO.class);
        System.out.println(newPart.toString());
    
        Transaction transaction = null;
        try {
            // Check if there's an active transaction and commit/rollback if necessary
            if (session.getTransaction() != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                System.err.println("Rolled back an active transaction before starting a new one.");
            }
    
            transaction = session.beginTransaction();
    
            // Check if the customer exists
            CustomerDTO existingCustomer = session.createQuery("FROM CustomerDTO WHERE dni = :dni", CustomerDTO.class)
                    .setParameter("dni", newPart.getDniCustomer())
                    .uniqueResult();
    
            // Check if the vehicle exists
            VehicleDTO existingVehicle = session.createQuery("FROM VehicleDTO WHERE plateNumber = :plateNumber", VehicleDTO.class)
                    .setParameter("plateNumber", newPart.getNumberPlate())
                    .uniqueResult();
    
            if (existingCustomer != null && existingVehicle != null) {
                System.out.println("Customer and vehicle exist!");
                session.save(newPart); // Save the new part to MM_PARTS table
                obj.setStatusProcess('P');
            } else {
                String missingEntity = (existingCustomer == null ? "Customer with DNI " + newPart.getDniCustomer() : "Vehicle with number plate " + newPart.getNumberPlate()) + " does not exist.";
                System.err.println(missingEntity + " Skipping part insertion.");
                obj.setErrorMessage(missingEntity + " Skipping part insertion.");
                obj.setOperation(Constantes.OPR_UPDATE);
                obj.setStatusProcess('E');
            }
    
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                System.err.println("Transaction rolled back due to an error.");
            }
            System.err.println("Error saving PartsDTO: " + e.getMessage());
            obj.setErrorMessage("Error saving PartsDTO: " + e.getMessage());
            obj.setStatusProcess('E');
        } finally {
            try {
                session.update(obj);
                System.out.println("InterfaceDTO updated successfully.");
            } catch (Exception e) {
                System.err.println("Error updating InterfaceDTO: " + e.getMessage());
            }
        }
    }
    
    

    private String getTranslation(String providerCode, String externalCode, String p_date) {
        try{
            System.out.println("GETS IN HERE!");
            TranslationDTO translation = HibernateUtil.getCurrentSession()
                    .createQuery("FROM TranslationDTO where " +
                            "ifnull(:p_date,current_date()) BETWEEN initializeDate AND ifnull(endDate,'2099-01-31') "
                            + "AND providerCode = ifnull(:p_prov, providerCode)" +
                            "AND externalCode = :externalCode ", TranslationDTO.class)
                    .setParameter("p_prov", providerCode)
                    .setParameter("p_date", p_date)
                    .setParameter("externalCode", externalCode )
                    .uniqueResult();
    
            System.out.println("Translation: " + translation);
            String internalCode = translation.getInternalCode();
            if (internalCode == null) {
                System.err.println("Internal code seems to be null!");
                return "";
            }
            return internalCode;
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        return "";
    }
}