package com.dam.tfg.MotoMammiApplicationIDS.Task;

import java.sql.Date;
import java.text.SimpleDateFormat;

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
            System.out.println("Reading Customer info!");
            //processImpl.readFileInfo(Constantes.C_CUSTOMERS, "SAN", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*     @Scheduled(cron = "${cron.time.schedule}")
    public void readInfoVehicle() {
        try {
            System.out.println("Reading Vehicle info");
            ps.readFileInfo(Constantes.C_VEHICLES, "", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "${cron.time.schedule}")
    public void readInfoParts() {
        try {
            System.out.println("Reading Parts info");
            ps.readFileInfo(Constantes.C_PARTS, "", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } */
}
