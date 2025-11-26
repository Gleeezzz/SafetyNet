package com.umbert.safetynet.service;

import com.umbert.safetynet.model.FireStation;
import com.umbert.safetynet.model.MedicalRecord;
import com.umbert.safetynet.model.Person;
import com.umbert.safetynet.repository.FireStationRepository;
import com.umbert.safetynet.repository.MedicalRecordsRepository;
import com.umbert.safetynet.repository.PersonRepository;
import com.umbert.safetynet.service.dto.ChildAlertDto;
import com.umbert.safetynet.service.dto.HouseholdMemberDto;
import com.umbert.safetynet.service.dto.PersonInfoDto;
import org.springframework.stereotype.Service;

import java.net.SocketOption;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;


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
        List<String> emails = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersons();
        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                emails.add(person.getEmail());

            }
        }
        return emails;
    }

    //Liste de String numero de telephone
    public List<String> findPhoneByNumber(String number) {
        List<String> phones = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersons();
        List<FireStation> fireStations = fireStationRepository.findAllFireStations();

        //Trouver toutes les addresses couvertes par cette caserne
        List<String> addresses = new ArrayList<>();
        for (FireStation fireStation : fireStations) {
            if (fireStation.getStation().equals(number)) {
                addresses.add(fireStation.getAddress());
            }
        }

        //FOR EACH IMBRIQUER
        for (Person person : persons) {
            for (String address : addresses) {

            }
            FireStation fireStation = new FireStation();
            if (person.getAddress().equals(fireStation.getAddress())) {
                phones.add(person.getPhone());
            }
        }
        return phones;
    }


    public PersonInfoDto getPersonInfoDTo(Person person, MedicalRecord medicalRecord) {

        // ... (Code de vérification/recherche des repositories si vous l'avez déplacé ici)

        // 1. Déclarer les variables de conversion (Maintenant au début)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate = LocalDate.parse(medicalRecord.getBirthdate(), formatter);

        // 2. Calculer l'âge
        LocalDate currentDate = LocalDate.now();
        int age = calculateAge(birthDate, currentDate);

        // 3. Retourner le DTO (La SEULE instruction de retour du DTO)
        return new PersonInfoDto(
                person.getFirstName(),
                person.getLastName(),
                age, // <-- Âge calculé
                person.getAddress(),
                person.getPhone(),
                person.getEmail(),
                medicalRecord.getMedications(),
                medicalRecord.getAllergies()
        );

    }

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {

        // La logique de calcul est correcte :
        return Period.between(birthDate, currentDate).getYears();
    }

    public List<ChildAlertDto> getChildAlertDto(String address) {  // ← Pas de <HouseholdMemberDto> ici !
        List<ChildAlertDto> result = new ArrayList<>();


        //Recuperer toutes les personnes à cette adresse
        List<Person> personsAtAddress = personRepository.findByAddress(address);
        System.out.println("1509 Culver St" + address);
        System.out.println("Jacob Boyd" + personsAtAddress.size());

        if (personsAtAddress.isEmpty()){
            return result;
        }
        //Declaration et initialisation de formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        //Pour chaque personne, calculer l'age et separer enfants/adultes
        for (Person person : personsAtAddress) {
            System.out.println("\n👤 Analyse de: " + person.getFirstName() + " " + person.getLastName());

            MedicalRecord medicalRecord = medicalRecordsRepository.findByFirstNameAndLastName(person.getFirstName(), person.getLastName());

            if (medicalRecord == null) {
                System.out.println("Pas de dossier medical trouvé");
                continue;
            }
            System.out.println("Dossier medical trouvé");
            System.out.println("Date de naissance" + medicalRecord.getBirthdate());

            // Conversion et calcul de l'age
            LocalDate birthdate = LocalDate.parse(medicalRecord.getBirthdate(), formatter);
            int age = PersonService.calculateAge(birthdate, LocalDate.now()); // Appel de la methode statique
            System.out.println("Age calculé" + age);

            if (age <= 18) { // Age inférieur ou egal à 18
                System.out.println("C'est un enfant !");
                // C'est un enfant - recuperer les autres membres du foyer
                List<HouseholdMemberDto> householdMembers = new ArrayList<>();
                for (Person otherPerson : personsAtAddress) {
                    if (!otherPerson.equals(person)) {
                        householdMembers.add(new HouseholdMemberDto(otherPerson.getFirstName(), otherPerson.getLastName()));
                        System.out.println("Membre du foyer ajouté " + otherPerson.getFirstName());
                    }
                }

                //Créer le DTO enfant avec la liste des autres membres
                ChildAlertDto child = new ChildAlertDto(
                        person.getFirstName(),
                        person.getLastName(),
                        age,
                        householdMembers
                );

                //IMPORTANT: Ajouter l'enfant à la liste résultat
                result.add(child);
                System.out.println("Enfant ajouté" + person.getFirstName());
            } else {
                System.out.println("Adulte (age>18)");
            }
        }

        System.out.println("Total enfants trovés" + result.size());
        return result;
    }


    // Dans PersonService.java (LA MÉTHODE MANQUANTE)

    public PersonInfoDto getPersonInfoDto(String firstName, String lastName) {

        // 1. Recherche : Utiliser vos Repositories
        // (Assurez-vous que findByFullName existe dans vos dépôts)
        Person person = (Person) personRepository.findByFullName(firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordsRepository.findByFullName(firstName, lastName);

        if (person == null || medicalRecord == null) {
            return null;
        }

        // 2. Conversion : Appel de la méthode de conversion déjà existante
        // Remarque : Votre méthode de conversion s'appelle 'getPersonInfoDTo' (avec DTo majuscule)
        return getPersonInfoDTo(person, medicalRecord);
    }
}



