package com.umbert.safetynet.service.dto;

public class PersonInfoDto {
    String firstName;
    String lastName;
    String email;
    String age;
    String address;

    private String[] medications;
    private String[] allergies;

    public PersonInfoDto(String firstName, String lastName, int age, String address, String phone, String email, String[] medications, String[] allergies) {

    }

    public String[] getMedications() { return medications; }

    public void setMedications(String[] medications) { this.medications = medications; }

    public String[] getAllergies() { return allergies; }

    public void setAllergies(String[] allergies) { this.allergies = allergies; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }


}
