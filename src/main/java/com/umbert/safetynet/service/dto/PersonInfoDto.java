package com.umbert.safetynet.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class PersonInfoDto {
    private String firstName;
    private String lastName;
    private String email;
    private int age;  // Changé de String à int
    private String address;
    private List<String> medications;
    private List<String> allergies;

    // Constructeur corrigé (sans phone, et qui assigne les valeurs)
    public PersonInfoDto(String firstName, String lastName, int age, String address,
                         String email, List<String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

    // Constructeur vide (utile pour Spring)
    public PersonInfoDto() {
    }

    //Getters and Setters


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}



