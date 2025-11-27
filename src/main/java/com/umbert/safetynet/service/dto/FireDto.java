package com.umbert.safetynet.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireDto {
    private String station;
    private String phoneNumber;
    private int age;
    private String lastName;
    private List<String> medications;
    private List<String> allergies;

}
