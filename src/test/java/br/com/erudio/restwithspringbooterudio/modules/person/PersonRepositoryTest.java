package br.com.erudio.restwithspringbooterudio.modules.person;

import br.com.erudio.restwithspringbooterudio.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PersonRepository repository;


    @Test
    void findAllByName() {
        Pageable pageable = PageRequest.of(
                0,
                5,
                Sort.by(Sort.Direction.ASC, "firstName")
        );

        Page<Person> personPage = repository.findAllByName("iko", pageable);

        assertThat(personPage).isNotNull();
        assertThat(personPage.getContent())
                .extracting(Person::getFirstName)
                .contains("Nikolaus");
    }
}
