package com.szrthk.facility_booking;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthcheck {
    @GetMapping("hc")
    public String healthcheck(){
        return ("System is working fine");
    }
}
