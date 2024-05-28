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
    
    @Scheduled(cron = "${cron.task.customer}")
    public void readInfoCustomers() {
        try {
            //processImpl.readFileInfo(Constantes.C_CUSTOMERS, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.task.vehicles}")
    public void readInfoVehicles() {
        try {
            //processImpl.readFileInfo(Constantes.C_VEHICLES, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.task.parts}")
    public void readInfoParts() {
        try {
            //processImpl.readFileInfo(Constantes.C_PARTS, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.task.integrate.customer}")
    public void processInfoCustomers() {
        try {
            //processImpl.integrateInfo(Constantes.C_CUSTOMERS, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.task.integrate.vehicles}")
    public void processInfoVehicles() {
        try {
            //processImpl.integrateInfo(Constantes.C_VEHICLES, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "${cron.task.integrate.parts}")
    public void processInfoParts() {
        try {
            //processImpl.integrateInfo(Constantes.C_PARTS, null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Every month
    @Scheduled(cron = "${cron.task.generate.invoice}")
    public void generateInvoice() {
        try {
            //processImpl.generateCsv(Constantes.C_CUSTOMERS, Constantes.PROV_CAX, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
