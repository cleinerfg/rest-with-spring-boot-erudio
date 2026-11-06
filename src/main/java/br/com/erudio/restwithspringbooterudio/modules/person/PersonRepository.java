package br.com.erudio.restwithspringbooterudio.modules.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id")
//    void disablePerson(@Param("id") Long id);
}
