package com.umbert.safetynet.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FireStationDto {
    Integer adultsCount;
    Integer childrenCount;
    List<FireStationPersonDto> fireStationPersonDtos;


    // String child, adults et lisyte de firestations

    private String children;
    private String adults;
    private List<FireStationDto> childrenList;

    public FireStationDto() {

    }

    public FireStationDto(String children, String adults) {
        this.children = children;
        this.adults = adults;
        this.childrenList = new ArrayList<FireStationDto>();
    }

    public String getChildren() {
        return children;
    }

    public void setChildscount(Integer childsCount) {
    }

    public void setAdultCount(Integer adultsCount) {

    }

    public List<Object> getPeople() {
        return null;
    }

    public void setPeople(List<FireStationPersonDto> people) {

    }
}
