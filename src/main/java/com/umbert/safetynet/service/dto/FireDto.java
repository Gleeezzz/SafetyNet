package com.umbert.safetynet.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireDto {
    private String firstName;
    private String lastName;
    private String station;
    private String phoneNumber;
    private int age;
    private List<String> medications;
    private List<String> allergies;

}
