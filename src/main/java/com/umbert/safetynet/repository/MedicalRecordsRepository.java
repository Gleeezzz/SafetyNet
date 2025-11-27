package com.umbert.safetynet.repository;

import com.umbert.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordsRepository {
    public static MedicalRecord findByFirstNameAndLastName(String firstName, String lastName)
    {
        return null;
    }

// (Ajustez ceci à la logique réelle de votre Repository, qui utilise probablement un DataHandler)

    public List<MedicalRecord> findByFullName(String firstName, String lastName) throws IOException {

        DataHandler dataHandler = new DataHandler();
        List<MedicalRecord> allRecords = dataHandler.getData().getMedicalRecords();
        return allRecords.stream()
                .filter(record ->
                        // 1. Vérifiez d'abord si les champs du MedicalRecord ne sont pas null
                        record.getFirstname() != null &&
                                record.getLastName() != null &&

                                // 2. Effectuez ensuite la comparaison robuste (en utilisant le paramètre d'entrée pour appeler la méthode)
                                firstName.equalsIgnoreCase((String) record.getFirstname()) &&
                                lastName.equalsIgnoreCase(record.getLastName()))

                .collect(Collectors.toList());
    }


    public List<MedicalRecord> findAllMedicalRecords() {
        return null;
    }
}
