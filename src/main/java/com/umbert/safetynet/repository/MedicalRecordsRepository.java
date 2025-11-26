package com.umbert.safetynet.repository;

import com.umbert.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordsRepository {
    public static MedicalRecord findByFirstNameAndLastName(String firstName, String lastName) {
        return null;
    }

    public MedicalRecord findByFullName(String firstName, String lastName) {
            return null;
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return null;
    }
}
