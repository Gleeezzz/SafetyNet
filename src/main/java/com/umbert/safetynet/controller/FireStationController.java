package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.service.dto.FireStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController
public class FireStationController {

    //private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }


    //get all fireStations
    @GetMapping("firestations")
    public List<FireStation> allFireStations() {
        return fireStationService.allFireStations();
    }


    @Autowired
    private FireStationService fireStationService;

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneAlert(@RequestParam String firestation) {
        List<String> phones = fireStationService.getPhoneNumbersByStation(firestation);
        return ResponseEntity.ok(phones);
    }
    //get the phones numbers by fireStations
   /* @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> phoneNumberList(@RequestParam(name = "fireStation") int number) {
        return this.fireStationService.findPhoneNumberByStationNumber(number);
    }

    */
}
