package com.umbert.safetynet.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Fournit Getters, Setters, toString, etc.
@AllArgsConstructor // Constructeur pour l'adresse et la liste
@NoArgsConstructor // Constructeur par défaut (bonne pratique)
public class FloodDto {

    // Champs pour l'adresse (Le Foyer)
    private String address;
    private List<PersonFloodInfo> persons; // Liste des personnes à cette adresse

    // Constructeur manuel pour correspondre à votre PersonService.getFloodStations (si Lombok ne suffit pas)
    /*
    public FloodDto(String address, List<PersonFloodInfo> persons) {
        this.address = address;
        this.persons = persons;
    }
    */


    // La classe interne/imbriquée qui représente les informations d'une PERSONNE
    @Data
    @AllArgsConstructor // Constructeur pour la personne
    @NoArgsConstructor
    public static class PersonFloodInfo { // ✅ DOIT ÊTRE STATIC

        // Champs pour la Personne
        private String firstName;
        private String lastName;
        private String phone; // Renommé de phoneNumber à phone pour correspondre au DTO PersonFloodInfo que vous utilisiez dans PersonService
        private int age;
        private List<String> medications;
        private List<String> allergies;

        /*
        // Constructeur manuel pour correspondre à l'appel dans PersonService :
        public PersonFloodInfo(String firstName, String lastName, String phone, int age, List<String> medications, List<String> allergies) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.age = age;
            this.medications = medications;
            this.allergies = allergies;
        }
        */
    }
}