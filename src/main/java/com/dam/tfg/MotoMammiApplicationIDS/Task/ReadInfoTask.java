package com.dam.tfg.MotoMammiApplicationIDS.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.Constantes;
import com.dam.tfg.MotoMammiApplicationIDS.services.implement.ProcessImpl;

@Component
public class ReadInfoTask {

    @Autowired
    private ProcessImpl processImpl = new ProcessImpl();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //Date p_Date = new Date(124-3-30);
    
    @Scheduled(cron = "${cron.time.schedule}")
    public void readInfoCustomers() {
        try {
            //processImpl.readFileInfo(Constantes.C_CUSTOMERS, "CAX", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.time.schedule}")
    public void processInfo() {
        try {
            //processImpl.integrateInfo(Constantes.PROV_CAX, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.time.schedule}")
    public void generateInvoice() {
        try {
            //processImpl.generateCsv(Constantes.C_CUSTOMERS, Constantes.PROV_CAX, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
