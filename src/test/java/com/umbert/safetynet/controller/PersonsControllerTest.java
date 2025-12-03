package com.umbert.safetynet.controller;

import com.umbert.safetynet.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonsControllerTest {

@Autowired
private PersonsController personsController;
@Autowired
    PersonRepository personRepository;

@BeforeAll
private static void setUp() throws Exception {

}





    @Test
    void allPersons() {
    }

    @Test
    void getChildAlert() {
    }

    @Test
    void getPersonInfo() {
    }

    @Test
    void getCommunityEmail() {
    Assertions.assertThat(personsController.getCommunityEmail("01/01/2000")).isNotNull();
    }
}
