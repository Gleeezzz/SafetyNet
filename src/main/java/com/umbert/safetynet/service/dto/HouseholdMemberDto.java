package com.umbert.safetynet.service.dto;

public class HouseholdMemberDto {
    private String firstName;
    private String lastName;

    public HouseholdMemberDto(){
    }

    public HouseholdMemberDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirsName() {
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
}
