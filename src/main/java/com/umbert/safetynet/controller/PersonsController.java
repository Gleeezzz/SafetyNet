package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.MedicalRecord;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.service.PersonService;
import com.umbert.safetynet.service.dto.ChildAlertDto;
import com.umbert.safetynet.service.dto.FloodDto;
import com.umbert.safetynet.service.dto.PersonInfoDto;  // Ton package exact
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PersonsController {

    private final PersonService personService;
    //Injection du service
    public PersonsController(PersonService personService) {
        this.personService = personService;
    }


    //get all persons
    @GetMapping("persons")
    public List<Person> allPersons(){
        return (List<Person>) personService.allPerson();
    }

    //get all emails
    @GetMapping("community/Email")
    public List<String> getEmails(){
        return personService.getAllEmails();
    }


    //get all email by city
    @RequestMapping(value = "communityEmail", method = RequestMethod.GET)
    public List<String> getEmails(@RequestParam(name = "city") String city){
        return personService.findAllEmailsByCity(city);
    }


    @GetMapping("/personinfo")
    public List<PersonInfoDto> getPersonInfo(
            @RequestParam String firstName,
            @RequestParam String lastName) {

        MedicalRecordsRepository personRepository = null;
        List<Person> persons = (List<Person>) personRepository.findByFirstNameAndLastName(firstName, lastName);

        // Vérification null
        if (persons == null || persons.isEmpty()) {
            return new ArrayList<>();  // Retourne une liste vide au lieu de planter
        }

        List<PersonInfoDto> result = new ArrayList<>();

        for (Person person : persons) {
            MedicalRecordsRepository medicalRecordRepository = null;
            MedicalRecord medicalRecord = medicalRecordRepository
                    .findByFirstNameAndLastName(person.getFirstName(), person.getLastName());

            if (medicalRecord != null) {  // Vérification aussi pour medicalRecord
                PersonInfoDto dto = personService.getPersonInfoDTO(person, medicalRecord);
                result.add(dto);
            }
        }

        return personService.getPersonInfo(firstName, lastName);
    }


    @GetMapping("/childAlert")
    public List<ChildAlertDto> getChildAlert(@RequestParam String address) {
        List<ChildAlertDto>  result = personService.getChildAlertDto(address);

        // Si aucun enfant trouvé, retourner une liste vide (ou 404 selon tes besoins)
        return (result);
    }

}



