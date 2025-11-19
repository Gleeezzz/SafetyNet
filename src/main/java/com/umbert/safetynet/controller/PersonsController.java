package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.service.dto.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsController<Emails> {

    private final PersonService personService;
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


}

