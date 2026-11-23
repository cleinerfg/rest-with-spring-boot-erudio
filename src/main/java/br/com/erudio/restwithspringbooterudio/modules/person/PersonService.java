package br.com.erudio.restwithspringbooterudio.modules.person;

import br.com.erudio.restwithspringbooterudio.exceptions.FileStorageException;
import br.com.erudio.restwithspringbooterudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbooterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbooterudio.modules.file.exporter.factory.FileExporterFactory;
import br.com.erudio.restwithspringbooterudio.modules.file.exporter.port.FileExporter;
import br.com.erudio.restwithspringbooterudio.modules.file.importer.factory.FileImporterFactory;
import br.com.erudio.restwithspringbooterudio.modules.file.importer.port.FileImporter;
import br.com.erudio.restwithspringbooterudio.modules.person.data.PersonMapper;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonService.class.getName());

    @Autowired
    private PersonRepository repository;

    @Autowired
    private FileImporterFactory fileImporterFactory;

    @Autowired
    private FileExporterFactory fileExporterFactory;

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
        addHateoasLinks(dto);
        return dto;
    }

    public PagedModel<EntityModel<PersonResponse>> findAll(Pageable pageable) {
        logger.info("Finding all persons!");

        Page<Person> personPage = repository.findAll(pageable);

        Page<PersonResponse> personResponsePage = personPage.map(entity -> {
            var dto = mapper.toResponse(entity);
            addHateoasLinks(dto);
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
            addHateoasLinks(dto);
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
        addHateoasLinks(dto);
        return dto;
    }

    public List<PersonResponse> massCreation(MultipartFile file) {
        logger.info("Importing all persons!");

        if (file.isEmpty()) throw new RequiredObjectIsNullException();

        try (InputStream inputStream = file.getInputStream()) {
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(RequiredObjectIsNullException::new);

            logger.info("File type: " + file.getContentType());

            FileImporter importer = fileImporterFactory.getImporter(filename);

            List<Person> entities = importer.importFile(inputStream).stream()
                    .map(request -> repository.save(mapper.toEntity(request)))
                    .toList();

            return entities.stream().map(entity -> {
                var dto = mapper.toResponse(entity);
                addHateoasLinks(dto);
                return dto;
            }).toList();
        } catch (Exception e) {
            throw new FileStorageException("Error processing file", e);
        }
    }

    public Resource exportPage(Pageable pageable, String acceptHeader) {
        logger.info("Exporting a people page!");

        List<Person> persons = repository.findAll(pageable).getContent();

        FileExporter fileExporter = fileExporterFactory.getExporter(acceptHeader);

        return fileExporter.export(persons);
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
        addHateoasLinks(dto);
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
        addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(PersonResponse dto) {
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
