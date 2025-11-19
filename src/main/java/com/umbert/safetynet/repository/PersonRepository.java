package com.umbert.safetynet.repository;

import com.umbert.safetynet.model.Data;
import com.umbert.safetynet.model.Person;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List; // ✅ BONNE IMPORTATION (Interface de Collection)
import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PersonRepository {

    private final DataHandler dataHandler;
    public PersonRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public List<Person> findAllPersons() {
        return dataHandler.getData().getPersons();
    }

    public List<String> getPhonesByAddresses(List<String> addresses) {
        Set<String> addressSet = new HashSet<>(addresses);

        Data data = dataHandler.getData();
        return data.getPersons().stream()
                .filter(person -> addressSet.contains(person.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .collect(Collectors.toList());
    }

}
