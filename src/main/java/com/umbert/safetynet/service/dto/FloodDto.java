package com.umbert.safetynet.service.dto;

import java.util.List;

public class FloodDto {
    private String address;
    private List<FloodPersonDto> floodPersonDtoList;

    //Constructeur par default
    public FloodDto() {
    }

    //Constructeur complet
    public FloodDto(String address, List<FloodPersonDto> persons) {
        this.address = address;
        this.floodPersonDtoList = persons;
    }

    //Getters and Setters


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FloodPersonDto> getFloodPersonDtoList() {
        return floodPersonDtoList;
    }

    public void setFloodPersonDtoList(List<FloodPersonDto> floodPersonDtoList) {
        this.floodPersonDtoList = floodPersonDtoList;
    }
}
