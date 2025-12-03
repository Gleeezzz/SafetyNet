package com.umbert.safetynet.repository;

import com.umbert.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordsRepository {

    // 🛑 AJOUT 1a : Déclarez et injectez le DataHandler (comme dans les autres Repositories)
    private final DataHandler dataHandler;

    // 🛑 AJOUT 1b : Constructeur pour l'injection
    public MedicalRecordsRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    // 🛑 SUPPRESSION : La méthode statique findByFirstNameAndLastName est inutile et incorrecte.
    // public static MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) { return null; }

    // Correction de findByFullName : retire la gestion manuelle de DataHandler et IOException.
    public List<MedicalRecord> findByFullName(String firstName, String lastName) {
        // La gestion des erreurs de chargement est faite dans DataHandler
        List<MedicalRecord> allRecords = dataHandler.getData().getMedicalRecords();

        return allRecords.stream()
                .filter(record ->
                        // Vrai nom de champ 'firstName' et 'lastName'
                        firstName.equalsIgnoreCase(record.getFirstName()) &&
                                lastName.equalsIgnoreCase(record.getLastName()))
                .collect(Collectors.toList());
    }

    // Correction pour la méthode utilisée dans le Service :
    // Elle renvoie un seul objet MedicalRecord ou null (plus facile à utiliser dans le Service).
    public MedicalRecord findUniqueByFullName(String firstName, String lastName) {
        List<MedicalRecord> allRecords = dataHandler.getData().getMedicalRecords();

        for (MedicalRecord record : allRecords) {
            if (record.getFirstName().equals(firstName) &&
                    record.getLastName().equals(lastName)) {
                return record;
            }
        }

        return null;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        // 🛑 CORRECTION : Retournez la liste complète
        return dataHandler.getData().getMedicalRecords();
    }

    public boolean findByFirstNameAndLastName(String firstName, String lastName) {
        return lastName.equalsIgnoreCase(firstName);
    }
}



