package com.umbert.safetynet.service.dto;

import lombok.Data;

import java.util.List;


public class FloodDto {
    private String address;
    private List<PersonFloodInfo> persons;

    public FloodDto() {
    }

    public FloodDto(String address, List<PersonFloodInfo> persons) {
        this.address = address;
        this.persons = persons;
    }

    // Getters et Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonFloodInfo> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonFloodInfo> persons) {
        this.persons = persons;
    }

    // Classe interne pour les infos de chaque personne
    public static class PersonFloodInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private String[] medications;
        private String[] allergies;



        public PersonFloodInfo(String firstName, String lastName, String phone,
                               int age, String[] medications, String[] allergies) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.age = age;
            this.medications = medications;
            this.allergies = allergies;
        }

        public PersonFloodInfo(String firstName, String lastName, String phone, int age, List<String> medications, List<String> allergies) {
        }

        // Getters et Setters
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String[] getMedications() { return medications; }
        public void setMedications(String[] medications) { this.medications = medications; }

        public String[] getAllergies() { return allergies; }
        public void setAllergies(String[] allergies) { this.allergies = allergies; }
    }
}
