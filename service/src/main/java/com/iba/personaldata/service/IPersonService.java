package com.iba.personaldata.service;

import com.iba.personaldata.entity.Person;
import com.iba.personaldata.util.Position;
import com.iba.personaldata.util.SortingType;
import org.springframework.data.domain.Page;

public interface IPersonService {

    Person save(Person person);

    Person findPersonByLogin(String login);

    Person edit(Person person, Person personDto);

    Page<Person> findAll(Integer page, SortingType sortingType);

    Page<Person> findPersonsByCriteria(String surname, Position position, Integer page);

    void delete(Long id);

    Person findOne(Long id);
}
