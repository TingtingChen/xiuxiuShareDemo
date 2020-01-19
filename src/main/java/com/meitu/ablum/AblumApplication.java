package com.meitu.ablum;

import com.meitu.ablum.service.serviceImpl.TesterServiceImpl;
import com.meitu.ablum.service.serviceImpl.VersiondataServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AblumApplication {

    public static void main(String[] args) {
        SpringApplication.run(AblumApplication.class, args);
        new TesterServiceImpl().getAllTester();
        new VersiondataServiceImpl().getAll();
    }

}
