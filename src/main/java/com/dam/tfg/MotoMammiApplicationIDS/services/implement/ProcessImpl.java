package com.dam.tfg.MotoMammiApplicationIDS.services.implement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationIDS.model.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@Component
public class ProcessImpl implements ProcessService{
    @Value("${path.file.customer.name}")
    private String customersNameFile;
    @Value("${path.file.vehicles.name}")
    private String vehiclesNameFile;
    @Value("${path.file.parts.name}")
    private String partsNameFile;

    @Override
    public void readFileInfo(String p_source, String p_prov, Date p_date) {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSession();

        String resource = "src/main/resources/in/";
        
        // Check if active and date in between initialized and end provider date.
        String getActiveSources = "FROM MM_PROVIDERS WHERE active = TRUE AND COALESCE(:p_date, CURRENT_DATE()) BETWEEN initializeDate AND COALESCE(endDate, '2099-12-30')";
        List<ProviderDTO> activeSources = HibernateUtil.getCurrentSession().createQuery(getActiveSources).setParameter("p_date", p_date).list();

        String providerCode = null;

        // initialize date if null, just like in query
        if (p_date == null) {
            p_date = new Date();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(p_date);
        String findCustomersFile = null;
        String findVehiclesFile = null;
        String findPartsFile = null;
        ArrayList<String> files = new ArrayList<>();

        for (ProviderDTO activeSource : activeSources) {
            providerCode = activeSource.getProviderCode();
                findCustomersFile = resource + customersNameFile + providerCode + "_" + formattedDate + ".dat";
                findVehiclesFile = resource + vehiclesNameFile + providerCode + "_" + formattedDate + ".dat";
                findPartsFile = resource + partsNameFile + providerCode + "_" + formattedDate + ".dat";

                files.add(findCustomersFile);
                //files.add(findVehiclesFile);
                //files.add(findPartsFile);

            for (String file : files) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    try {
                        br.readLine(); // skip first line (column names)
                        while ((line = br.readLine()) != null) {
                            System.out.println("Reading file " + file + ": \n" + line + "\n");
                            
                        }
                    } catch (IOException e) {
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
