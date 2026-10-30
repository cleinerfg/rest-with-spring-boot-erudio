package br.com.erudio.restwithspringbooterudio.repositories;

import br.com.erudio.restwithspringbooterudio.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
