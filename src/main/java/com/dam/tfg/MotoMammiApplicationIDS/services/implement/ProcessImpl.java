package com.dam.tfg.MotoMammiApplicationIDS.services.implement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.HibernateUtil;
import com.dam.tfg.MotoMammiApplicationIDS.model.ProviderDTO;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@Component
public class ProcessImpl implements ProcessService{
    @Override
    public void readFileInfo(String p_source, String p_prov, Date p_date) {
        HibernateUtil.buildSessionFactory();
        HibernateUtil.openSession();

        String resource = "src/main/resources/in";
        
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


        for (ProviderDTO activeSource : activeSources) {
            providerCode = activeSource.getProviderCode();
            String findFile = "MM_invoices_" + providerCode + "_" + formattedDate + ".dat";
            System.out.println("File path: " + findFile);

            try {
                BufferedReader br = new BufferedReader(new FileReader(findFile));
                int line;
                try {
                    while ((line = br.read()) != -1) {
                        System.out.println("Reading: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                System.err.println("No se encontro el fichero! " + findFile);
            }
        }

        

        // need COD_PROV, Source and p_date to read from file
        


        
        // Process the results
        
        HibernateUtil.closeSessionFactory();
    }
}
