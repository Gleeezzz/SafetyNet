package com.umbert.safetynet.service.dto;

import lombok.Data;

@Data
public class ChildDto {
    private String firstName;
    private String lastName;
    private int age;

    // Constructeur pour l'initialisation dans le service
    public ChildDto(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

        //Getters and Setters
        public String getFirstName () {
            return firstName;
        }

        public void setFirstName (String firstName){
            this.firstName = firstName;
        }

        public String getLastName () {
            return lastName;
        }

        public void setLastName (String lastName){
            this.lastName = lastName;
        }

        public int getAge () {
            return age;
        }

        public void setAge ( int age){
            this.age = age;
        }

    }
