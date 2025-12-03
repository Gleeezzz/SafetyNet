package com.umbert.safetynet.model;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    private String firstName;
    private String lastName;
    private List<String> medications;
    private List<String> allergies;
    private String birthdate;
}



