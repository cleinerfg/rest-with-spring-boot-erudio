package br.com.erudio.restwithspringbooterudio.modules.person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<Person, Long> {

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
//    void disablePerson(@Param("id") Long id);

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE LOWER(CONCAT ('%', :firstName, '%'))")
    Page<Person> findAllByName(String firstName, Pageable pageable);
}
