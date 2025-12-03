package com.umbert.safetynet.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class ChildAlertDto {
    private String firstName;
    private String lastName;
    private int age;
    private List<HouseholdMemberDto> otherMembers;

    // Constructeurs
    public ChildAlertDto() {}

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<HouseholdMemberDto> getOtherMembers() {
        return otherMembers;
    }

    public void setOtherMembers(List<HouseholdMemberDto> otherMembers) {
        this.otherMembers = otherMembers;
    }

    public ChildAlertDto(String firstName, String lastName, int age) {

    }

}










