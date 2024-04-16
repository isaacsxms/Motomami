package com.dam.tfg.MotoMammiApplicationIDS.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dam.tfg.MotoMammiApplicationIDS.Utils.Constantes;
import com.dam.tfg.MotoMammiApplicationIDS.services.ProcessService;

@Component
public class ReadInfoTask {

    @Autowired
    private ProcessService ps;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(cron = "${cron.time.schedule}")
    public void readInfoCustomers() {
        try {
            System.out.println("Reading Customer info!");
            ps.readFileInfo(Constantes.C_CUSTOMERS, "", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "${cron.time.schedule}")
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
    }
}
