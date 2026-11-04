package br.com.erudio.restwithspringbooterudio.modules.person;

import br.com.erudio.restwithspringbooterudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbooterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbooterudio.modules.person.data.PersonMapper;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        var dto = PersonMapper.toResponse(entity);
        addHateosLinks(dto);
        return dto;
    }

    public List<PersonResponse> findAll() {
        logger.info("Finding all persons!");
        var entities = repository.findAll();
        var dtos = entities.stream()
                .map(PersonMapper::toResponse)
                .toList();

        dtos.forEach(PersonService::addHateosLinks);
        return dtos;
    }

    public PersonResponse create(PersonRequest request) {

        if (request == null) throw new RequiredObjectIsNullException();

        logger.info("Creating person!");
        Person entity = PersonMapper.toEntity(request);
        Person savedEntity = repository.save(entity);
        var dto = PersonMapper.toResponse(savedEntity);
        addHateosLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting person!");

        if (!repository.existsById(id))
            throw new ResourceNotFoundException("No records found with id: " + id);
        repository.deleteById(id);
    }

    public PersonResponse update(Long id, PersonRequest request) {
        if (request == null) throw new RequiredObjectIsNullException();

        logger.info("Updating person!");
        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );

        entity.setFirstName(request.firstName());
        entity.setLastName(request.lastName());
        entity.setAddress(request.address());
        entity.setGender(request.gender());

        Person updatedEntity = repository.save(entity);
        var dto = PersonMapper.toResponse(updatedEntity);
        addHateosLinks(dto);
        return dto;
    }

    private static void addHateosLinks(PersonResponse dto) {
        Long id = dto.getId();

        dto.add(linkTo(methodOn(PersonController.class).getById(id))
                .withSelfRel()
                .withType("GET")
        );

        dto.add(linkTo(methodOn(PersonController.class).getAll())
                .withRel("getAll")
                .withType("GET")
        );

        dto.add(linkTo(methodOn(PersonController.class).create(null))
                .withRel("create")
                .withType("POST")
        );

        dto.add(linkTo(methodOn(PersonController.class).update(id, null))
                .withRel("update")
                .withType("PUT")
        );

        dto.add(linkTo(methodOn(PersonController.class).delete(id))
                .withRel("delete")
                .withType("DELETE")
        );
    }
}
