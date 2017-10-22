package com.iba.personaldata.dao;

import com.iba.personaldata.entity.Person;
import com.iba.personaldata.entity.PersonRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by test on 15.10.2017.
 */
public interface PersonRoleRepository extends JpaRepository<PersonRole, Long> {
    @Modifying
    @Query("delete from PersonRole pr where pr.person = :person")
    void deleteByPersonId(@Param("person") Person person);
}
