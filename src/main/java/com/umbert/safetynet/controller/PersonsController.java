package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.service.FireStationService;
import com.umbert.safetynet.service.PersonService;
import com.umbert.safetynet.service.dto.ChildAlertDto;
import com.umbert.safetynet.service.dto.PersonInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping

public class PersonsController {

    @Autowired
    private FireStationService fireStationService;

    private final PersonService personService;

    // Injection du service
    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    // Get all persons
    @GetMapping("/persons")
    public List<Person> allPersons() {
        return personService.allPerson();
    }


    // 2. Get child alert by address
    @GetMapping("/childAlert")
    public ResponseEntity<?> getChildAlert(@RequestParam String address) {
        List<ChildAlertDto> result = personService.getChildAlertByAddress(address);

        if (result == null || result.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(result);
    }


    // 6. Get person info by firstName and lastName
    @GetMapping("/personinfo")
    public List<PersonInfoDto> getPersonInfo(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        return personService.getPersonInfo(firstName, lastName);
    }


    // 7. Get community emails by city (GARDE SEULEMENT CELUI-CI)
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam String city) {
        List<String> emails = personService.findAllEmailsByCity(city);
        return ResponseEntity.ok(emails);
    }
}