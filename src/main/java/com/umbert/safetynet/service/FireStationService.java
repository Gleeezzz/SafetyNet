package com.umbert.safetynet.service;


import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.model.MedicalRecord;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import com.umbert.safetynet.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
        List<MedicalRecord> medicalRecords = medicalRecordsRepository.findAllMedicalRecords();

        //get all people
        List<Person> persons = personRepository.findAllPersons();
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
                    if ((computeAge(medicalRecord.getBirthdate()) <= 18))
                    childsCount++;
                } else {
                    adultsCount++;
                }
            }
            result.getPeople().add(fireStationPersonDto);
        }


        result.setAdultCount(adultsCount);
        result.setChildscount(childsCount);
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
            if (medicalRecord.getFirstName().equals(person.getFirstName())
                    && medicalRecord.getLastName().equals(person.getLastName())) {
                return medicalRecord;
            }
        }
        return null;
    }

    private int computeAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(birthdate, formatter);
        return PersonService.calculateAge(birthDate, LocalDate.now());
    }


    public List<FloodDto> flood(List<Integer> stationNumbers) {
        List<FloodDto> result = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //Pour chaque numero de station
        for (Integer stationNumber : stationNumbers) {
            //Recuperer toutes les casernes avec ce numero
            List<FireStation> fireStations = fireStationRepository.findAllFireStationsByNumber(stationNumber);

            //Pour chaque caserne, recuperer les addresses
            for (FireStation fireStation : fireStations) {
                String address = fireStation.getAddress();

            //Recuperer toutes les personnes de cette adresse
            List<Person> personAtAddress = personRepository.findByAddress(address);
            List<FloodPersonDto> floodPersons = new ArrayList<>();

                // Pour chaque personne
                for (Person person : personAtAddress) {
                    MedicalRecord medicalRecord = medicalRecordsRepository.findByFirstNameAndLastName(
                            person.getFirstName(),
                            person.getLastName()
                    );

                    if (medicalRecord != null) {
                        LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), formatter);
                        int age = PersonService.calculateAge(birthdate, LocalDate.now());

                        FloodPersonDto floodPerson = new FloodPersonDto(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getPhone(),
                                age,
                                List.of(medicalRecord.getMedications()),
                                List.of(medicalRecord.getAllergies())
                        );
                        floodPersons.add(floodPerson);
                    }
                }

                // Créer le FloodDto pour cette adresse
                FloodDto floodDto = new FloodDto(address, floodPersons);
                result.add(floodDto);
            }
        }

        return result;
    }

    // ✅ MÉTHODE addFireStation IMPLÉMENTÉE
    public void addFireStation(FireStation fireStation) {
        fireStationRepository.save(fireStation);
    }
}







