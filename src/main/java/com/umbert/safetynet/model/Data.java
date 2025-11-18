package com.umbert.safetynet.model;

import java.util.List;

public class Data {

    private List <Person> persons;
    private List <FireStation> firestations;
    private List <MedicalRecord> medicalrecords;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<FireStation> getFireStations() {
        return firestations;
    }

    public void setFireStations(List<FireStation> fireStations) {
        this.firestations= firestations;
    }

    public List<MedicalRecord> getMedicalrecords() {
        return medicalrecords;
    }

    public void setMedicalrecords(List<MedicalRecord> medicalrecords) {
        this.medicalrecords = medicalrecords;
    }
}
