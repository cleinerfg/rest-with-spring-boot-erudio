package br.com.erudio.restwithspringbooterudio.modules.person;

import br.com.erudio.restwithspringbooterudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbooterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbooterudio.modules.person.data.PersonMapper;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonResponse> assembler;

    @Autowired
    PersonMapper mapper;

    public PersonResponse findById(Long id) {
        logger.info("Finding person by id!");
        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );
        var dto = mapper.toResponse(entity);
        addHateosLinks(dto);
        return dto;
    }

    public PagedModel<EntityModel<PersonResponse>> findAll(Pageable pageable) {
        logger.info("Finding all persons!");

        Page<Person> personPage = repository.findAll(pageable);

        Page<PersonResponse> personResponsePage = personPage.map(entity -> {
            var dto = mapper.toResponse(entity);
            addHateosLinks(dto);
            return dto;
        });

        Link getAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                        .getAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())
                        )
        ).withSelfRel();

        return assembler.toModel(personResponsePage, getAllLink);
    }

    public PagedModel<EntityModel<PersonResponse>> findByName(String firstName, Pageable pageable) {
        logger.info("Finding people by name!");

        Page<Person> personPage = repository.findAllByName(firstName, pageable);

        Page<PersonResponse> personResponsePage = personPage.map(entity -> {
            var dto = mapper.toResponse(entity);
            addHateosLinks(dto);
            return dto;
        });

        Link getAllLink = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class)
                        .getAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                String.valueOf(pageable.getSort())
                        )
        ).withSelfRel();

        return assembler.toModel(personResponsePage, getAllLink);
    }

    public PersonResponse create(PersonRequest request) {

        if (request == null) throw new RequiredObjectIsNullException();

        logger.info("Creating person!");
        Person entity = mapper.toEntity(request);
        Person savedEntity = repository.save(entity);
        var dto = mapper.toResponse(savedEntity);
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
        var dto = mapper.toResponse(updatedEntity);
        addHateosLinks(dto);
        return dto;
    }

    public PersonResponse disable(Long id) {
        logger.info("Disabling person!");

        Person entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );

        entity.setEnabled(false);
        repository.save(entity);

        var dto = mapper.toResponse(entity);
        addHateosLinks(dto);
        return dto;
    }

    private static void addHateosLinks(PersonResponse dto) {
        Long id = dto.getId();

        dto.add(linkTo(methodOn(PersonController.class).getById(id))
                .withSelfRel()
                .withType("GET")
        );

        dto.add(linkTo(methodOn(PersonController.class).getAll(null, null, null))
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

        dto.add(linkTo(methodOn(PersonController.class).disable(id))
                .withRel("disable")
                .withType("PATCH")
        );

        dto.add(linkTo(methodOn(PersonController.class).delete(id))
                .withRel("delete")
                .withType("DELETE")
        );
    }
}
