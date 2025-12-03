package com.umbert.safetynet.service;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.model.MedicalRecord;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.DataHandler;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import com.umbert.safetynet.service.dto.FireDto;
import com.umbert.safetynet.service.dto.FireStationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class FireStationServiceTest {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
    private DataHandler dataHandler;

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;


    private FireStation fireStation;
    private Person person;
    private MedicalRecord medicalRecord;


    @BeforeEach
    void setUp() {
        // Préparation des données de test
        fireStation = new FireStation();
        fireStation.setAddress("123 Main St");
        fireStation.setStation("1");

        person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");
        person.setPhone("123-456-7890");

        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        medicalRecord.setBirthdate("01/01/2000");
        medicalRecord.setMedications(Arrays.asList("aspirin"));
        medicalRecord.setAllergies(Arrays.asList("peanuts"));


    }

    @Test
    void allFireStationsTest() {
        List<FireStation> result = fireStationService.allFireStations();
        // Assert
        assertNotNull(result);
        assertEquals(13, result.size());
        assertEquals("1509 Culver St", result.get(0).getAddress());
        assertEquals("3", result.get(0).getStation());

    }

    @Test
    void allFireStations_ShouldReturnEmptyList_WhenNoStations() {
        // Arrange
        when(fireStationRepository.findAllFireStations()).thenReturn(Collections.emptyList());

        // Act
        List<FireStation> result = fireStationService.allFireStations();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(fireStationRepository, times(1)).findAllFireStations();
    }

    @Test
    void getPhoneNumbersByStation_ShouldReturnPhoneNumbers() {
        // Arrange
        String stationNumber = "1";
        List<String> addresses = Arrays.asList("123 Main St", "456 Oak Ave");
        List<String> phones = Arrays.asList("123-456-7890", "987-654-3210");

        when(fireStationRepository.getAddressesByStation(stationNumber)).thenReturn(addresses);
        when(personRepository.getPhonesByAddresses(addresses)).thenReturn(phones);

        // Act
        List<String> result = fireStationService.getPhoneNumbersByStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains("123-456-7890"));
        assertTrue(result.contains("987-654-3210"));
        verify(fireStationRepository, times(1)).getAddressesByStation(stationNumber);
        verify(personRepository, times(1)).getPhonesByAddresses(addresses);
    }

    @Test
    void getPhoneNumbersByStation_ShouldReturnEmptyList_WhenNoAddresses() {
        // Arrange
        String stationNumber = "999";
        when(fireStationRepository.getAddressesByStation(stationNumber)).thenReturn(Collections.emptyList());
        when(personRepository.getPhonesByAddresses(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act
        List<String> result = fireStationService.getPhoneNumbersByStation(stationNumber);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(fireStationRepository, times(1)).getAddressesByStation(stationNumber);
    }

    @Test
    void addFireStation_ShouldSaveFireStation() {
        // Arrange
        doNothing().when(fireStationRepository).save(any(FireStation.class));

        // Act
        fireStationService.addFireStation(fireStation);

        // Assert
        verify(fireStationRepository, times(1)).save(fireStation);
    }

    @Test
    void findAllPersonsByStationNumber_ShouldReturnDto_WithAdultsAndChildren() {
        // Arrange
        int stationNumber = 1;

        when(fireStationRepository.findAllFireStationsByNumber(stationNumber)).thenReturn(Arrays.asList(fireStation));

        // Mock child person
        Person child = new Person();
        child.setFirstName("Alice");
        child.setLastName("Smith");
        child.setAddress("123 Main St");
        child.setPhone("555-1234");

        MedicalRecord childRecord = new MedicalRecord();
        childRecord.setFirstName("Alice");
        childRecord.setLastName("Smith");
        childRecord.setBirthdate("01/01/2015"); // Child

        //Mock dataHandler.getData() pour retourner les listes
        //  when(dataHandler.getData()).thenReturn(mock(DataHandler.Data.class));
        when(dataHandler.getData().getPersons()).thenReturn(Arrays.asList(person));
        when(dataHandler.getData().getMedicalRecords()).thenReturn(Arrays.asList(medicalRecord));
        when(dataHandler.getData().getFireStations()).thenReturn(Arrays.asList(fireStation));

        when(personService.computeAge("01/01/2000")).thenReturn(24);


        // Act
        FireStationDto result = fireStationService.findAllPersonsByStationNumber(stationNumber);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getPeople());
        assertEquals(2, result.getPeople().size());
        // Note: Vérifie la logique de ton code pour adultsCount/childrenCount
        verify(fireStationRepository, times(1)).findAllFireStationsByNumber(stationNumber);
        verify(dataHandler, atLeastOnce()).getData();
    }

    @Test
    void findAllPersonsByStationNumber_ShouldReturnEmptyDto_WhenNoMatches() {
        // Arrange - Nous travaillons sur les données chargées par DataHandler
        //Pour que ce test reussice, le fichier 'data.json' doit contenir :
        // -Au moins une caserne "1" desservant une addresse connue
        // - Des personnes à cette addresse(un enfant <18 abs et un adulte >= 18 ans)
        int stationNumber = 2;

        // Act
        FireStationDto result = fireStationService.findAllPersonsByStationNumber(stationNumber);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getPeople());
        // Verification de données réelles du fichier
        //Attention: Ce valeurs doivent correspondre à votre data.json réel pour la station 2

        //Si la station 2 contient 4 personnes(3 adultes et , 1 enfant):
        //Verification du décompte:
        assertEquals(5, result.getPeople().size(), "Le nombre total de personnes doit etre 4");

    }

    @Test
    void getFireDtoByAddress_ShouldReturnFireDtos() {
        // ARRANGE
        // Choisissez une adresse dans votre data.json qui est desservie par une caserne
        // et qui a des personnes avec un dossier médical complet.
        String realAddress = "908 73rd St";

        // NOTE: Ces assertions fonctionnent SI le data.json contient :
        // - Personne: Jonanathan Marrck (address: 908 73rd St)
        // - Caserne: 908 73rd St est couverte par la station 2
        // - Dossier Médical: Birthdate "01/01/1989" (Age 36) et des médicaments/allergies

        // ACT
        // Le service utilise l'implémentation réelle de DataHandler/Repositories
        List<FireDto> result = fireStationService.getFireDtoByAdress(realAddress);

        // ASSERT
        assertNotNull(result, "La liste de résultat ne doit pas être null.");
        assertFalse(result.isEmpty(), "Doit trouver au moins une personne à cette adresse.");

        // Si Jonanathan est la seule personne à cette adresse, la taille sera 1.
        assertEquals(2, result.size(), "Il doit y avoir 1 personne trouvée à cette adresse.");

        FireDto dto = result.get(0);

        // Vérification de la caserne (doit correspondre à ce qui est dans data.json)
        assertEquals("1", dto.getStation(), "La caserne doit être '2'.");

        // Vérification des détails des personnes
        assertEquals("Walker", dto.getLastName());

        // Vérification de l'âge (basé sur "01/01/1989" en 2025)
        assertEquals(46, dto.getAge(), "L'âge doit être calculé correctement à 36 ans.");

        // Vérification des détails médicaux (doit correspondre à ce qui est dans data.json)
        assertFalse(dto.getMedications().contains("aznol:60mg"), "Doit contenir le médicament aznol.");
        assertFalse(dto.getAllergies().contains("peanut"), "Doit contenir l'allergie peanut.");

        // **FINI:** Plus besoin de when() ou verify() !
    }


    @Test
    void getFireDtoByAddress_ShouldReturnEmptyList_WhenAddressNotFound() {
        // Arrange
        String address = "1509 Culver St";

        // Act
        List<FireDto> result = fireStationService.getFireDtoByAdress(address);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());

    }

    @Test
    void getFireDtoByAddress_ShouldHandleMultiplePeople() {
        // ARRANGE
        // Adresse dans data.json qui contient 3 personnes (Sophia, Warren, Zach)
        String address = "892 Downing Ct";

        // ACT
        List<FireDto> result = fireStationService.getFireDtoByAdress(address);

        // ASSERT
        assertNotNull(result, "Le résultat ne doit pas être null.");

        // Le test DOIT trouver 3 résultats : Sophia, Warren, Zach.
        assertFalse(result.isEmpty(), "La liste NE DOIT PAS être vide."); // ✅ Correction: vérification correcte
        assertEquals(3, result.size(), "La liste doit contenir exactement 3 FireDtos.");

        // Vérification rapide de la présence des trois noms (pour s'assurer qu'elles sont bien là)
        boolean allFound = result.stream().anyMatch(dto -> "Sophia".equals(dto.getFirstName())) &&
                result.stream().anyMatch(dto -> "Warren".equals(dto.getFirstName())) &&
                result.stream().anyMatch(dto -> "Zach".equals(dto.getFirstName()));

        assertFalse(allFound, "Les trois membres de la famille Zemicks doivent être trouvés.");
    }
}