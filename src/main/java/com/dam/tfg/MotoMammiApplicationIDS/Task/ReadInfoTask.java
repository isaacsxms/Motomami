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
    //Date date1 = dateFormat.parse("2013-07-17");

    @Scheduled(cron = "${cron.time.schedule}" )
    public void readInfoCustomers() {
        try {
            System.out.println("Hello World!");
            ps.readFileInfo(Constantes.C_CUSTOMERS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
