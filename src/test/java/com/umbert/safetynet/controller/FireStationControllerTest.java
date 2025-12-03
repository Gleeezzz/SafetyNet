package com.umbert.safetynet.controller;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.service.FireStationService;
import com.umbert.safetynet.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private FireStationService fireStationService;

    @MockitoBean
    private PersonService personService;

    @Test
    void allFireStations() throws Exception {
        // Arrange
        FireStation station1 = new FireStation();
        station1.setAddress("123 Main St");
        station1.setStation("1");

        FireStation station2 = new FireStation();
        station2.setAddress("456 Oak Ave");
        station2.setStation("2");

        List<FireStation> stations = Arrays.asList(station1, station2);




    }

    @Test
    void personsListByFireStation() throws Exception {
        // Arrange - on mock simplement sans vérifier le contenu du DTO
        when(fireStationService.findAllPersonsByStationNumber(anyInt())).thenReturn(any());

        // Act & Assert
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "1"))
                .andExpect(status().isOk());

        verify(fireStationService, times(1)).findAllPersonsByStationNumber(1);
    }

    @Test
    void getPhoneAlert() throws Exception {
        // Arrange
        List<String> phones = Arrays.asList("123-456-7890", "987-654-3210");

        when(fireStationService.getPhoneNumbersByStation(anyString())).thenReturn(phones);

        // Act & Assert
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]").value("123-456-7890"));

        verify(fireStationService, times(1)).getPhoneNumbersByStation("2");
    }

    @Test
    void getFireStation() throws Exception {
        // Arrange - on mock simplement une liste vide
        when(fireStationService.getFireDtoByAdress(anyString())).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/fire")
                        .param("address", "123 Main St"))
                .andExpect(status().isOk());

        verify(fireStationService, times(1)).getFireDtoByAdress("123 Main St");
    }

    @Test
    void flood() throws Exception {
        // Arrange - on mock simplement une Map vide
        when(personService.getFloodStations(anyList())).thenReturn(Collections.emptyMap());

        // Act & Assert
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "1", "2"))
                .andExpect(status().isOk());

        verify(personService, times(1)).getFloodStations(anyList());
    }

    @Test
    void addFirestation() throws Exception {
        // Arrange
        String jsonContent = """
            {
                "address": "789 New St",
                "station": "3"
            }
            """;

        doNothing().when(fireStationService).addFireStation(any(FireStation.class));

        // Act & Assert
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        verify(fireStationService, times(1)).addFireStation(any(FireStation.class));
    }
}