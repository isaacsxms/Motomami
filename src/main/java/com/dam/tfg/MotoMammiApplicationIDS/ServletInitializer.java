package com.dam.tfg.MotoMammiApplicationIDS;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(MotoMammiApplicationIDS.class); // later change to correct name
}
}