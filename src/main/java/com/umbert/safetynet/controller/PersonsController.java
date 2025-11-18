package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.service.dto.PersonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonsController {

    private final PersonService personService;
    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    //get all persons
    @GetMapping("persons")
    public List<Person> allPersons(){
        return (List<Person>) personService.allPerson();
    }
}
