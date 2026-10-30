package br.com.erudio.restwithspringbooterudio.modules.person;

import br.com.erudio.restwithspringbooterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbooterudio.modules.person.data.PersonMapper;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    public PersonResponse findById(Long id) {
        logger.info("Finding person by id!");
        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );
        return PersonMapper.toResponse(entity);
    }

    public List<PersonResponse> findAll() {
        logger.info("Finding all persons!");
        return repository.findAll().stream()
                .map(PersonMapper::toResponse)
                .toList();
    }

    public PersonResponse create(PersonRequest request) {
        logger.info("Creating person!");
        Person entity = PersonMapper.toEntity(request);
        Person savedEntity = repository.save(entity);
        return PersonMapper.toResponse(savedEntity);
    }

    public void delete(Long id) {
        logger.info("Deleting person!");

        if (!repository.existsById(id))
            throw new ResourceNotFoundException("No records found with id: " + id);
        repository.deleteById(id);
    }

    public PersonResponse update(Long id, PersonRequest request) {
        logger.info("Updating person!");
        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );

        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setAddress(request.address());
        entity.setGender(request.gender());

        Person updatedEntity = repository.save(entity);
        return PersonMapper.toResponse(updatedEntity);
    }
}
