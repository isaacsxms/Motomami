package com.dam.tfg.MotoMammiApplicationIDS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.dam.tfg.MotoMammiApplicationIDS.Task.ReadInfoTask;

@SpringBootApplication
@EnableScheduling
public class MotoMammiApplicationIDS {

	public static void main(String[] args) {
		SpringApplication.run(MotoMammiApplicationIDS.class, args);
	}
}
