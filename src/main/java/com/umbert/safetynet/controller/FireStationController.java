package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.service.FireStationService;
import com.umbert.safetynet.service.PersonService;
import com.umbert.safetynet.service.dto.FireDto;
import com.umbert.safetynet.service.dto.FireStationDto;
import com.umbert.safetynet.service.dto.FloodDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
public class FireStationController {

    private final FireStationService fireStationService;
    private final PersonService personService;

    // 🎯 CORRECTION : Un seul constructeur qui injecte TOUTES les dépendances requises
    public FireStationController(FireStationService fireStationService, PersonService personService) {
        this.fireStationService = fireStationService;
        this.personService = personService;
    }


    // Get all fireStations
    @GetMapping("/firestations")
    public List<FireStation> allFireStations() {
        return fireStationService.allFireStations();
    }


    // Get the persons by fireStation (first name, lastName, address, phone number, count adults/children)
    @GetMapping("/firestation")
    public FireStationDto personsListByFireStation(@RequestParam(name = "stationNumber") int number) {
        return this.fireStationService.findAllPersonsByStationNumber(number);
    }


    //  Get the phone numbers by firestation number
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam String firestation) {
        List<String> phones = fireStationService.getPhoneNumbersByStation(firestation);
        return ResponseEntity.ok(phones);
    }


    //  Get fire information by address (FIX: ajout du slash)
    @GetMapping("/fire")
    public List<FireDto> getFireStation(@RequestParam String address) {
        return fireStationService.getFireDtoByAdress(address);
    }

    // Get flood information for multiple stations
    @GetMapping("/flood/stations")
    public Map<String, List<FloodDto>> flood(@RequestParam(name = "stations") List<Integer> numbers) {
        // Note : Le type de retour doit correspondre à celui de PersonService (Map<String, List<FloodDto>>)
        return this.personService.getFloodStations(numbers);
    }


    // Add a fireStation
    @PostMapping("/firestation")
    public void addFirestation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
    }


}





