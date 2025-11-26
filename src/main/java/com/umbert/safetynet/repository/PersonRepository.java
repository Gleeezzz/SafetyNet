package com.umbert.safetynet.repository;

import com.umbert.safetynet.model.Data;
import com.umbert.safetynet.model.Person;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List; // ✅ BONNE IMPORTATION (Interface de Collection)
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

    public List<Person> findByAddress(String address) {
        return dataHandler.getData().getPersons().stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());
    }


    public List<Person> findByFullName(String firstName, String lastName) {
        return dataHandler.getData().getPersons().stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName))
                .filter(person -> person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }


}
