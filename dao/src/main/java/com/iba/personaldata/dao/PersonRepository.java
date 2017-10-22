package com.iba.personaldata.dao;

import com.iba.personaldata.entity.Person;
import com.iba.personaldata.util.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("select b from Person b where b.login = :login")
    Person findByName(@Param("login") String login);

    Person findPersonByLogin(String login);

    @Query("select b from Person b where (b.surname = :surname or :surname is null) and (b.position = :position or :position is null)")
    Page<Person> findPersonsByCriteria(@Param("surname") String surname, @Param("position") Position position, Pageable pageRequest);

}
