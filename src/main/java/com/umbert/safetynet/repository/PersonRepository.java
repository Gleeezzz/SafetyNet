package com.umbert.safetynet.repository;

import com.umbert.safetynet.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Component
public class PersonRepository {

    private final DataHandler dataHandler;
    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPerson() {
        return dataHandler.getData().getPersons();
    }
}
