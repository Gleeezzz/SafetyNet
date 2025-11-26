package com.umbert.safetynet.repository;


import com.umbert.safetynet.model.FireStation;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;



@Component
public class FireStationRepository {

    private final DataHandler dataHandler;

    public FireStationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<FireStation> findAllFireStations() {
        return dataHandler.getData().getFireStations();
    }

    public List<String> getAddressesByStation(String stationNumber) {
        return dataHandler.getData().getFireStations().stream()
                .filter(station -> station.getStation().equals(stationNumber))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());
    }

    public List<FireStation> findAllFireStationsByNumber(Integer number) {
        return dataHandler.getData().getFireStations().stream()
                .filter(f -> f.getStation()
                .equals(number.toString())).collect(Collectors.toList());
    }

    public FireStation findFireStationByNumber(String address   ) {
        return dataHandler.getData().getFireStations().stream()
                .filter(p -> p.getAddress().equals(address)).findFirst().orElseGet(() -> new FireStation());
    }

    public void save(FireStation fireStation) {

    }
}



