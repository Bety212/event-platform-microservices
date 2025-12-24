package com.eventplatform.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients        // ➜ pour utiliser Feign
public class ReservationserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationserviceApplication.class, args);
    }
}
