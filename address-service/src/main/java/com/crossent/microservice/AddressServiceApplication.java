package com.crossent.microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.sql.DataSource;

@EnableDiscoveryClient
@SpringBootApplication
public class AddressServiceApplication {


    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(AddressServiceApplication.class, args);
    }

}
