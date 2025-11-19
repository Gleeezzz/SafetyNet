package com.umbert.safetynet.service.dto;


import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FireStationService {

   // private final FireStationRepository fireStationRepository;
    //private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;

    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }


    public List<FireStation> allFireStations() {
        return fireStationRepository.findAllFireStations();
    }

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<String> getPhoneNumbersByStation(String stationNumber) {
        // Récupère les adresses de la caserne
        List<String> addresses = fireStationRepository.getAddressesByStation(stationNumber);

        // Récupère les téléphones des personnes à ces adresses
        return personRepository.getPhonesByAddresses(addresses);
    }

    }

