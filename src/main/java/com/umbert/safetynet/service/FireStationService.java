package com.umbert.safetynet.service;


import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.model.MedicalRecord;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.DataHandler;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import com.umbert.safetynet.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashSet;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {


    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    private final MedicalRecordsRepository medicalRecordsRepository;
    @Autowired
    private DataHandler dataHandler;
    @Autowired
    private PersonService personService;


    public FireStationService(FireStationRepository fireStationRepository, PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository) {
        this.fireStationRepository = fireStationRepository;
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
    }


    public List<FireStation> allFireStations() {
        return fireStationRepository.findAllFireStations();
    }


    public List<String> getPhoneNumbersByStation(String stationNumber) {
        // Récupère les adresses de la caserne
        List<String> addresses = fireStationRepository.getAddressesByStation(stationNumber);

        // Récupère les téléphones des personnes à ces adresses
        return personRepository.getPhonesByAddresses(addresses);
    }

    public FireStationDto findAllPersonsByStationNumber(int number) {
        FireStationDto result = new FireStationDto();
        List<FireStationPersonDto> people = new ArrayList<>();
        result.setPeople(people);


        //get all stations by number
        List<FireStation> firestations = fireStationRepository.findAllFireStationsByNumber(number);
        List<MedicalRecord> medicalRecords = dataHandler.getData().getMedicalRecords();

        //get all people
        List<Person> persons = dataHandler.getData().getPersons();
        Integer childsCount = 0;
        Integer adultsCount = 0;
        //Compare addresses and add results in FireStationDto
        for (Person person : persons) {
            FireStationPersonDto fireStationPersonDto = null;
            if (fireStationContainPersons(firestations, person) != null) {
                fireStationPersonDto = new FireStationPersonDto();
                fireStationPersonDto.setFirstName(person.getFirstName());
                fireStationPersonDto.setLastName(person.getLastName());
                fireStationPersonDto.setAddress(person.getAddress());
                fireStationPersonDto.setPhoneNumber(person.getPhone());

                MedicalRecord medicalRecord = medicalRecordsCointainsPerson(medicalRecords, person);
                if (medicalRecord != null) {
                    if ((personService.computeAge(medicalRecord.getBirthdate()) <= 18))
                        childsCount++;
                } else {
                    adultsCount++;
                }

                result.getPeople().add(fireStationPersonDto);
            }
        }


        result.setAdultsCount(adultsCount);
        result.setChildrenCount(childsCount);
        return result;
    }

    //Methodes manquantes:

    private FireStation fireStationContainPersons(List<FireStation> firestations, Person person) {
        for (FireStation fireStation : firestations) {
            if (fireStation.getAddress().equals(person.getAddress())) {
                return fireStation;
            }
        }
        return null;
    }

    private MedicalRecord medicalRecordsCointainsPerson(List<MedicalRecord> medicalRecords, Person person) {
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (medicalRecord.getLastName().equals(person.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }



    // ✅ MÉTHODE addFireStation IMPLÉMENTÉE
    public void addFireStation(FireStation fireStation) {
        fireStationRepository.save(fireStation);
    }

    public List<FireDto> getFireDtoByAdress(String address) {
        List<FireStation> fireStations = dataHandler.getData().getFireStations();
        List<MedicalRecord> medicalRecords = dataHandler.getData().getMedicalRecords();
        List<Person> persons = dataHandler.getData().getPersons();

        List<FireDto> result = new ArrayList<>();
        for (FireStation fs : fireStations) {

            // Si la fire station correspond à l'adresse recherchée
            if (fs.getAddress().equals(address)) {
                String stationNumber = fs.getStation();
                //sauvegarde ce numéro dans une variable temporaire

                // Parcours de toutes les personnes pour trouver celles à cette adresse
                for (Person p : persons) {
                    // Si la personne habite à l'adresse recherchée
                    if (p.getAddress().equals(address)) {

                        // Création d'un nouveau DTO pour cette personne
                        FireDto dto = new FireDto();

                        dto.setStation(stationNumber);
                        dto.setPhoneNumber(p.getPhone());
                        dto.setLastName(p.getLastName());

                        // Parcours des dossiers médicaux pour trouver celui de cette personne
                        for (MedicalRecord mr : medicalRecords) {

                            // Si le dossier médical correspond à la personne
                            if (p.getLastName().equals(mr.getLastName())) {
                                dto.setAge(personService.computeAge(mr.getBirthdate()));
                                dto.setAllergies(mr.getAllergies());
                                dto.setMedications(mr.getMedications());
                            }
                        }

                        result.add(dto);
                    }
                }
            }
        }
        // Retour de la liste contenant tous les DTOs
        return result;
    }
}






