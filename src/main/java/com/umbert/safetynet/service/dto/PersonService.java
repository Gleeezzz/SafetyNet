package com.umbert.safetynet.service.dto;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final FireStationRepository fireStationRepository;

    public PersonService(PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository, FireStationRepository fireStationRepository) {
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.fireStationRepository = fireStationRepository;
    }

    public List<Person> allPerson() {
        return personRepository.findAllPersons();
    }

    public List<String> getAllEmails() { // Nouvelle méthode correcte
        List<Person> persons = personRepository.findAllPersons(); // Récupère toutes les personnes

        //façon avec "stream"
        //return personRepository.findAllPersons()stream().filter(Person p -> p.getCity().equals(city)).map(Person p -> p.getEmail()).collector(Collector.toList());


        // Initialise la liste des emails
        List<String> emails = new ArrayList<>();

        // Boucle sur toutes les personnes
        for (Person person : persons) {
            // Assurez-vous que votre objet Person a bien une méthode getEmail()
            emails.add(person.getEmail());
        }

        return emails;
    }

    public List<String> findAllEmailsByCity(String city) {
           List<String> emails = new ArrayList<>();
           List<Person> persons = personRepository.findAllPersons();
           for (Person person : persons) {
               if (person.getCity().equals(city)){
                   emails.add(person.getEmail());

               }
           }
        return emails;



    }
}
