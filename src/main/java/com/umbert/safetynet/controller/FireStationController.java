package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.service.dto.FireStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }


    //get all fireStations
    @GetMapping("firestations")
    public List<FireStation> allFireStations() {
        return fireStationService.allFireStations();
    }


    //get the phones numbers by fireStations
   /* @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> phoneNumberList(@RequestParam(name = "fireStation") int number) {
        return this.fireStationService.findPhoneNumberByStationNumber(number);
    }

    */
}
