package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.service.FireStationService;
import com.umbert.safetynet.service.dto.FireStationDto;
import com.umbert.safetynet.service.dto.FloodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    // Get all fireStations
    @GetMapping("/firestations")
    public List<FireStation> allFireStations() {
        return fireStationService.allFireStations();
    }

    // Get the phone numbers by firestation number
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam String firestation) {
        List<String> phones = fireStationService.getPhoneNumbersByStation(firestation);
        return ResponseEntity.ok(phones);
    }

    // Get the persons by fireStation (first name, lastName, address, phone number, count adults/children)
    @GetMapping("/firestation")
    public FireStationDto personsListByFireStation(@RequestParam(name = "stationNumber") int number) {
        return this.fireStationService.findAllPersonsByStationNumber(number);
    }

    // Get flood information for multiple stations
    @GetMapping("/flood/stations")
    public List<FloodDto> flood(@RequestParam(name = "stations") List<Integer> numbers) {
        return this.fireStationService.flood(numbers);
    }

    // Add a fireStation
    @PostMapping("/firestation")
    public void addFirestation(@RequestBody FireStation fireStation) {
        fireStationService.addFireStation(fireStation);
    }
}





