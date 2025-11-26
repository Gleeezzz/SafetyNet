package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.service.PersonService;
import com.umbert.safetynet.service.dto.ChildAlertDto;
import com.umbert.safetynet.service.dto.PersonInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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


    @GetMapping("personinfo")
    public List<PersonInfoDto> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        PersonInfoDto infoDto = personService.getPersonInfoDto(firstName, lastName);
        if (infoDto == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(infoDto);
    }


    @GetMapping("/childAlert")
    public List<ChildAlertDto> getChildAlert(@RequestParam String address) {
        List<ChildAlertDto>  result = personService.getChildAlertDto(address);

        // Si aucun enfant trouvé, retourner une liste vide (ou 404 selon tes besoins)
        return (result);
    }
}

