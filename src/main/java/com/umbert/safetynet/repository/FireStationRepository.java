package com.umbert.safetynet.repository;


import com.umbert.safetynet.model.FireStation;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class FireStationRepository {

    private final DataHandler dataHandler;

    public FireStationRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<FireStation> findAllFireStations() {

        return dataHandler.getData().getFireStations();
    }

}
