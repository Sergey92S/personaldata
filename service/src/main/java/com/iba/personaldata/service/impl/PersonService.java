package com.iba.personaldata.service.impl;

import com.iba.personaldata.dao.PersonRepository;
import com.iba.personaldata.dao.PersonRoleRepository;
import com.iba.personaldata.dao.RoleRepository;
import com.iba.personaldata.entity.Person;
import com.iba.personaldata.entity.PersonRole;
import com.iba.personaldata.service.IPersonService;
import com.iba.personaldata.util.Position;
import com.iba.personaldata.util.RoleType;
import com.iba.personaldata.util.SortingType;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PersonRoleRepository personRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${app.limit.persons}")
    private int maxPersonsOnPage = 10;

    public Person findByName(String name) {
        return personRepository.findByName(name);
    }

    public Person save(Person person) {
        person.setActive(1);
        Calendar c = Calendar.getInstance();
        Calendar.getInstance().set(Calendar.DAY_OF_MONTH, 1);
        person.setExperience(c.getTime());
//        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        PersonRole personRole = new PersonRole();
        person = personRepository.save(person);
        personRole.setPerson(person);
        personRole.setRole(roleRepository.findByRole(RoleType.USER));
        personRoleRepository.save(personRole);
        return person;
    }

    public Person findPersonByLogin(String login) {
        Person person = personRepository.findPersonByLogin(login);
        Hibernate.initialize(person.getPersonRoleSet());
        return person;
    }

    public Person edit(Person person, Person personDto) {
        if (personRepository.findPersonByLogin(personDto.getLogin()) == null || person.getLogin().equals(personDto.getLogin())) {
            person = personRepository.getOne(person.getId());
            person.setName(personDto.getName());
            person.setSurname(personDto.getSurname());
            person.setLogin(personDto.getLogin());
            person.setPassword(personDto.getPassword());
            person = personRepository.save(person);
        } else {
            person = null;
        }
        return person;
    }

    public Page<Person> findAll(Integer page, SortingType sortingType) {
        if (page == null || page < 1 ) {
            page = 1;
        }
        int startPosition = (page - 1) * maxPersonsOnPage;
        Page<Person> persons = SortingType.NONE.name().equals(sortingType.name()) ? personRepository.findAll(new PageRequest(startPosition, page * maxPersonsOnPage)) : personRepository.findAll(new PageRequest(startPosition, page * maxPersonsOnPage, new Sort(new Sort.Order(Sort.Direction.ASC, sortingType.name().toLowerCase()))));
        return persons;
    }

    public Page<Person> findPersonsByCriteria(String surname, Position position, Integer page) {
        if (page == null || page < 1 ) {
            page = 1;
        }
        int startPosition = (page - 1) * maxPersonsOnPage;
        return (surname == null && position == null) ? null : personRepository.findPersonsByCriteria(surname, position, new PageRequest(startPosition, page * maxPersonsOnPage));
    }

    public void delete(Long id) {
        personRoleRepository.deleteByPersonId(personRepository.findOne(id));
        personRepository.delete(id);
    }

    public Person findOne(Long id) {
        return personRepository.findOne(id);
    }

}
