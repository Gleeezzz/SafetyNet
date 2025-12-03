package com.umbert.safetynet.service;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.model.MedicalRecord;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import com.umbert.safetynet.service.dto.*;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final MedicalRecordsRepository medicalRecordsRepository;
    private final FireStationRepository fireStationRepository;


    public PersonService(PersonRepository personRepository, MedicalRecordsRepository medicalRecordsRepository, FireStationRepository fireStationRepository) {
        this.personRepository = personRepository;
        this.medicalRecordsRepository = medicalRecordsRepository;
        this.fireStationRepository = fireStationRepository;
    }


    public List<Person> allPerson() {
        return personRepository.findAllPersons();
    }

    public List<String> getAllEmails() { // Nouvelle méthode correcte
        List<Person> persons = personRepository.findAllPersons(); // Récupère toutes les personnes

        //façon avec "stream" -by JC-
        //return personRepository.findAllPersons()stream().filter(Person p -> p.getCity().equals(city)).map(Person p -> p.getEmail()).collector(Collector.toList());


        // Initialise la liste des emails
        List<String> emails = new ArrayList<>();

        // Boucle sur toutes les personnes
        for (Person person : persons) {
            // Assurez-vous que votre objet Person a bien une méthode getEmail()
            emails.add(person.getEmail());
        }

        return emails;
    }

    public List<String> findAllEmailsByCity(String city) {
        List<String> emails = new ArrayList<>();// Filtrer par ville et recuperer les emails
        List<Person> persons = personRepository.findAllPersons();// Recuperer tout les personnes
        for (Person person : persons) {
            if (person.getCity() != null &&
                    person.getCity().equalsIgnoreCase(city) &&
                    person.getEmail() != null &&
                    !person.getEmail().isEmpty()) {
                // Éviter les doublons
                if (!emails.contains(person.getEmail())) {
                    emails.add(person.getEmail());
                }
            }
        }

        return emails;
    }

    // ✅ Correction pour findPhoneByNumber
    public List<String> findPhoneByNumber(String number) {
        List<String> phones = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersons();

        // 1. Trouver toutes les addresses couvertes par cette caserne
        List<String> addresses = fireStationRepository.findAllFireStations().stream()
                .filter(fs -> fs.getStation().equals(number))
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        // 2. Filtrer les personnes par adresse
        for (Person person : persons) {
            if (addresses.contains(person.getAddress())) { // ✅ Vérifie si l'adresse est dans la liste
                phones.add(person.getPhone());
            }
        }
        return phones;
    }


    // La méthode doit retourner une LISTE !
    public List<PersonInfoDto> getPersonInfo(String firstName, String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();

        // 1. Trouver toutes les personnes
        List<Person> personList = personRepository.findByFullName(firstName, lastName);

        if (personList == null || personList.isEmpty()) {
            return result; // Retourne une liste vide
        }

        // 2. Boucle pour convertir chaque personne en DTO
        for (Person person : personList) {
            // ... (Logique pour récupérer MedicalRecord)
            List<MedicalRecord> recordsFound = medicalRecordsRepository.findByFullName(
                    person.getFirstName(),
                    person.getLastName()
            );

            // Assurez-vous d'utiliser findFirst().orElse(null) pour l'extraction :
            MedicalRecord medicalRecord = recordsFound.stream().findFirst().orElse(null);

            // ... (Utilisation de la méthode de conversion)
            PersonInfoDto dto = convertToPersonInfoDto(person, medicalRecord);
            result.add(dto);
        }

        return result; // Retourne la liste
    }

    // Dans PersonService.java (dans la méthode convertToPersonInfoDto)

    public PersonInfoDto convertToPersonInfoDto(Person person, MedicalRecord medicalRecord) {

        PersonInfoDto dto = new PersonInfoDto();

        // 🛑 CORRECTION 1 : Assignation des champs de l'objet Person
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setAddress(person.getAddress());
        dto.setEmail(person.getEmail());

        // 🛑 CORRECTION 2 : Logique pour l'âge et les listes (déjà faite, mais vérifiez qu'elle est là)
        if (medicalRecord != null) {
            // Logique pour l'âge, les médicaments et les allergies
            // (Assurez-vous que computeAge est bien résolu en public dans PersonService)
            int age = computeAge(medicalRecord.getBirthdate());
            dto.setAge(age);
            dto.setMedications(medicalRecord.getMedications());
            dto.setAllergies(medicalRecord.getAllergies());
        } else {
            // Valeurs par défaut si le dossier médical est manquant
            dto.setAge(0);
            dto.setMedications(Collections.emptyList());
            dto.setAllergies(Collections.emptyList());
        }

        return dto;
    }

    public List<ChildAlertDto> getChildAlertByAddress(String address) {
        System.out.println("🔍 1509 Culver St: '" + address + "'");

        // Récupérer toutes les personnes à cette adresse
        List<Person> personsAtAddress = personRepository.findByAddress(address);
        System.out.println("👥 Jacob Boyd: " + (personsAtAddress != null ? personsAtAddress.size() : 0));



        if (personsAtAddress == null || personsAtAddress.isEmpty()) {
            System.out.println("❌ DEBUG - Aucune personne trouvée à cette adresse");
            return Collections.emptyList();
        }

        //Declaration de listes(utilisation Dtos externes)
        List<ChildAlertDto> children = new ArrayList<>();// Liste pour stocker les enfants trouvés

        List<Person> otherMembers = new ArrayList<>();// Liste pour stocker les autres membres du foyer

        // Parcourir toutes les personnes
        ChildAlertDto child = null;
        for (Person person : personsAtAddress) {

            MedicalRecord medicalRecord = medicalRecordsRepository.findUniqueByFullName(
                    person.getFirstName(),
                    person.getLastName()
            );

            System.out.println("🔍 DEBUG - Medical record trouvé pour " + person.getFirstName() + ": " + (medicalRecord != null));


            if (medicalRecord != null) {
                int age = computeAge(medicalRecord.getBirthdate());

                if (age <= 18) {
                    // C'est un enfant
                    child = new ChildAlertDto();
                    child.setFirstName(person.getFirstName());
                    child.setLastName(person.getLastName());
                    child.setAge(age);
                    children.add(child);
                    //List<Person> houseHolds = new ArrayList<>();
                } else {
                    // C'est un adulte
                    otherMembers.add(person);
                }
            }
        }
        // Si aucun enfant n'est trouvé, retourner une liste vide

        if (children.isEmpty()) {
            System.out.println("ℹ️ DEBUG - Aucun enfant trouvé à cette adresse");
            return Collections.emptyList();
        }

        // Deuxième passe : ajouter les autres membres du foyer à chaque enfant
        for (ChildAlertDto childDto : children) {
            List<HouseholdMemberDto> householdMembers = new ArrayList<>();

            for (Person member : otherMembers) {
                HouseholdMemberDto householdMember = new HouseholdMemberDto();
                householdMember.setFirstName(member.getFirstName());
                householdMember.setLastName(member.getLastName());
                householdMembers.add(householdMember);
            }

            childDto.setOtherMembers(householdMembers);
        }
        return children;
    }


public Map<String, List<FloodDto>> getFloodStations(List<Integer> stationNumbers) {
    Map<String, List<FloodDto>> result = new HashMap<>();

    for (Integer stationNumber : stationNumbers) {
        // 1. Trouver toutes les adresses desservies par cette caserne
        List<String> addresses = fireStationRepository.getAddressesByStation(stationNumber.toString());

        List<FloodDto> floodData = new ArrayList<>();

        for (String address : addresses) {
            // 2. Pour chaque adresse, trouver les habitants
            List<Person> persons = personRepository.findByAddress(address);
            if (persons == null || persons.isEmpty()) {
                continue;
            }

            List<FloodDto.PersonFloodInfo> personInfos = new ArrayList<>();


            for (Person person : persons) {
                // 3. Récupérer les infos médicales
                List<MedicalRecord> recordsFound = medicalRecordsRepository
                        .findByFullName(person.getFirstName(), person.getLastName());

                //Extraire l'objet unique (MedicalRecord) de la liste
                MedicalRecord medicalRecord = recordsFound.stream().findFirst().orElse(null);

                if (medicalRecord != null) {
                    int age = computeAge(medicalRecord.getBirthdate());
                    FloodDto.PersonFloodInfo personInfo = new FloodDto.PersonFloodInfo(
                            person.getFirstName(),
                            person.getLastName(),
                            person.getPhone(),
                            age,
                            medicalRecord.getMedications(),
                            medicalRecord.getAllergies()
                    );

                    personInfos.add(personInfo);
                }
            }

            // 4. Créer le FloodDto pour cette adresse
            if (!personInfos.isEmpty()) {

                FloodDto floodDto = new FloodDto(address, personInfos);
                floodData.add(floodDto);
            }
        }

        // 5. Ajouter au résultat avec la station comme clé
        result.put("Station " + stationNumber, floodData);
    }

    return result;
}

// Dans PersonService.java (méthode computeAge)

public static int computeAge(String birthdateOfPerson) {
    // 🛑 Modifiez le format pour qu'il corresponde à vos données (généralement dd/MM/yyyy dans ce projet)
    LocalDate dob = LocalDate.parse(birthdateOfPerson, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

    LocalDate curDate = LocalDate.now();
    return Period.between(dob, curDate).getYears();
}
}










